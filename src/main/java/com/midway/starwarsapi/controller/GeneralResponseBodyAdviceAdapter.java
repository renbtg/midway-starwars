package com.midway.starwarsapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class GeneralResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {
    private static final Logger logger = LogManager.getLogger(GeneralResponseBodyAdviceAdapter.class);

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (! logger.isDebugEnabled()) {
            return o; // logging of response body only when debugging
        }

        if (serverHttpRequest instanceof ServletServerHttpRequest &&
                serverHttpResponse instanceof ServletServerHttpResponse) {

            ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
            ServletServerHttpResponse response = (ServletServerHttpResponse) serverHttpResponse;

            ObjectMapper mapper = new ObjectMapper();
            String jsonBody;
            try {
                jsonBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            } catch (JsonProcessingException e) {
                jsonBody = o.toString();
                logger.debug("Error converting object of class " + o.getClass().getName() +
                        " to JSON string", e);
            }

            logger.debug("MIDWAY STARWARS-API HTTP RESPONSE: status=[" + response.getServletResponse().getStatus() + "]" +
                    ", body=[" + System.lineSeparator() + jsonBody + "]");
        }

        return o;
    }
}
