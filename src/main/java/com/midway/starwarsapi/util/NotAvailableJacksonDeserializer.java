package com.midway.starwarsapi.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class NotAvailableJacksonDeserializer<T extends Number> extends StdScalarDeserializer<T> {
    private Number prototype;

    public NotAvailableJacksonDeserializer(T prototype) {
        super(prototype.getClass());
        this.prototype = prototype;
    }


    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        final String val = jsonParser.getValueAsString();

        if ("N/A".equalsIgnoreCase(val) || "unknown".equalsIgnoreCase(val)) {
            return null;
        }

        return instantiateNumber(val);
    }

    private T instantiateNumber(String val) {
        if (prototype instanceof BigDecimal) {
            var retVal = (T) new BigDecimal(val);
            return retVal;
        } else if (prototype instanceof Long) {
            var retVal = (T) (Long) (Long.parseLong(Util.justDigits(val)));
            return retVal;
        } else if (prototype instanceof Integer) {
            var retVal = (T) (Integer) (Integer.parseInt(Util.justDigits(val)));
            return retVal;
        } else {
            return null; // unkownw numeric type
        }
    }
}