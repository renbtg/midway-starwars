package com.midway.starwarsapi.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

@EnableWebMvc
@ControllerAdvice
public class GeneralRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {
    private static final Logger logger = LogManager.getLogger(GeneralRequestBodyAdviceAdapter.class);

    @Autowired
    HttpServletRequest httpServletRequest; // never mind IntelliJ painting red, it's a bug in the IDE

    public GeneralRequestBodyAdviceAdapter() {
        int qq=1;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        String strParameters = ControllerUtils.getHttpParametersString(httpServletRequest);
        logger.info("MIDWAY STARWARS-API HTTP REQUEST BODY: method=[" + httpServletRequest.getMethod() + "]" +
                ", URI=[" + httpServletRequest.getRequestURI() + "]" +
                ", parameters=[" + strParameters + "]" +
                ", body=[" + ((body==null)?"":body)+"]"
        );

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
