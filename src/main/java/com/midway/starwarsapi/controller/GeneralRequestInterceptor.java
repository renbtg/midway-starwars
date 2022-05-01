package com.midway.starwarsapi.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GeneralRequestInterceptor implements HandlerInterceptor {
    private static final Logger logger = LogManager.getLogger(GeneralRequestInterceptor.class);

    public GeneralRequestInterceptor() {
        int i = 0;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {


        String strParameters = ControllerUtils.getHttpParametersString(request);

        logger.info("MIDWAY STARWARS-API HTTP REQUEST RECEIVED, method=[" + request.getMethod() + "]" +
                ", URI=[" + request.getRequestURI() + "]" +
                ", parameters=" +
                "[" + strParameters + "]"
        );

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) {
        logger.info("MIDWAY STARWARS-API HTTP RESPONSE SENT: status=[" + response.getStatus() + "]");
    }

}
