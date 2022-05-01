package com.midway.starwarsapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.midway.starwarsapi.constants.DateTimeConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.python.bouncycastle.util.Strings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.Spliterator;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Util {
    public static final long ONE_GIGABYTE = 1024L * 1024 * 1024;
    private static final Logger logger = LogManager.getLogger(Util.class);

    /**
     * Returns 'true' if both collections have same elements in any order. Duplicate elements do
     * not cause 'false' to be returned.
     */
    public static boolean sameCollectionElements(Collection<?> c1, Collection<?> c2) {
        if (c1 == null) {
            c1 = new ArrayList<>();
        }
        if (c2 == null) {
            c2 = new ArrayList<>();
        }

        var l1 = new ArrayList<>(c1);
        var l2 = new ArrayList<>(c2);
        boolean l1HasL2 = l1.containsAll(l2);
        boolean l2HasL1 = l2.containsAll(l1);

        return l1HasL2 && l2HasL1;
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
                .map(ldt -> Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()))
                .orElse(null);
    }

    public static Date toTimeWithoutDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        LocalTime localTime = LocalTime.of(localDateTime.getHour(), localDateTime.getMinute(),
                localDateTime.getSecond(), localDateTime.getNano());
        //NO TIMEZONE WORRIES here

        /*
         * OCV-194: years before 1915 (TIMEZONE_LOW_YEAR have LocalTime distortions (timezone internationalization)
         * OCV-194: add milliseconds present in localDateTime.getNano()

         */

        Instant instant = localTime.atDate(LocalDate.of(DateTimeConstants.TIMEZONE_LOW_YEAR, 1, 1)).
                atZone(ZoneId.systemDefault()).toInstant();

        return Date.from(instant);
    }

    public static Date toDate(LocalDate ld) {
        if (ld == null) {
            return null;
        }
        return new GregorianCalendar(ld.getYear(), ld.getMonth().getValue() - 1, ld.getDayOfMonth()).getTime();
    }


    public static String listJoin(Collection<? extends Object> collection, String separator, String lastSeparator) {
        if (collection == null) {
            return null;
        }
        if (collection.isEmpty()) {
            return "";
        }
        if (separator == null) {
            separator = " ";
        }
        if (lastSeparator == null) {
            lastSeparator = separator;
        }
        StringBuilder sb = new StringBuilder();
        int i = -1;
        for (Object el : collection) {
            i++;
            String str;
            if (el == null) {
                str = "";
            } else {
                str = el.toString();
            }
            sb.append(str);
            if (i < collection.size() - 2) {
                sb.append(separator);
            } else if (i == collection.size() - 2) {
                sb.append(lastSeparator);
            }
        }
        return sb.toString();
    }

    public static boolean bigDecimalEquals(BigDecimal b1, BigDecimal b2) {
        if ((b1 == null && b2 != null) || (b1 != null && b2 == null)) {
            return false; // distinct nullity for min
        }
        if (b1 == null) {
            // if any of them is null, so both are null
            return true;
        }
        return b1.compareTo(b2) == 0;
    }

    public static UUID uuidOrNull(String uuidStr) {
        if (uuidStr == null) {
            return null;
        } else {
            return UUID.fromString(uuidStr);
        }
    }

    public static String blankIfNull(String string) {
        return Optional.ofNullable(string).orElse("");
    }

    public static String getFileChecksum(File file) throws IOException {
        String shaChecksum;
        try {
            MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] byteArray = new byte[1024 * 1024 * 512];
                int bytesCount;
                //Read file data and update in message digest
                while ((bytesCount = fis.read(byteArray)) != -1) {
                    shaDigest.update(byteArray, 0, bytesCount);
                }
            }
            //Get the hash's bytes and convert it to hexadecimal format
            byte[] bytes = shaDigest.digest();
            StringBuilder builder = new StringBuilder();
            for (byte aByte : bytes) {
                builder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            shaChecksum = builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("No such algorithm for message digest SHA-256", e);
        }
        return shaChecksum;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static <T> Stream<T> getStreamFromIterable(Iterable<T> iterable)
    {

        // Convert the Iterable to Spliterator
        Spliterator<T>
                spliterator = iterable.spliterator();

        // Get a Sequential Stream from spliterator
        return StreamSupport.stream(spliterator, false);
    }

    public static ObjectMapper getObjectMapper() {
        // TODO - register module and create mapper only once? May be thread-unsafe.
        SimpleModule simpleModule = new SimpleModule()
                .addDeserializer(BigDecimal.class, new NotAvailableJacksonDeserializer(BigDecimal.ZERO))
                .addDeserializer(Long.class, new NotAvailableJacksonDeserializer(0L))
                .addDeserializer(Integer.class, new NotAvailableJacksonDeserializer(0));
        ObjectMapper om = new ObjectMapper()
                .registerModule(simpleModule)
                .registerModule(new JavaTimeModule());
        return om;
    }

    public static int getNumberFromUrl(String url) {
           var pieces = url.split("/");
           String lastPiece = pieces[pieces.length-1];
           var lastPieceSplitted = lastPiece.split("=");
           return Integer.parseInt(lastPieceSplitted[lastPieceSplitted.length-1]);
    }

    public static String justDigits(String str) {
        return str.replaceAll("[^0-9]", "");
    }
}
