package com.midway.starwarsapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Util {
    private static final Logger logger = LogManager.getLogger(Util.class);


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

    public static String blankIfNull(String string) {
        return Optional.ofNullable(string).orElse("");
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
                .addDeserializer(BigDecimal.class, new NotAvailableJacksonDeserializer<>(BigDecimal.ZERO))
                .addDeserializer(Long.class, new NotAvailableJacksonDeserializer<>(0L))
                .addDeserializer(Integer.class, new NotAvailableJacksonDeserializer<>(0));
        return new ObjectMapper()
                .registerModule(simpleModule)
                .registerModule(new JavaTimeModule());
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
