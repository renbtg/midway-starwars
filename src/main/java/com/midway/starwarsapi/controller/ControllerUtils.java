package com.midway.starwarsapi.controller;

import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerUtils {
    public static String getHttpParametersString(HttpServletRequest request) {
        HttpServletRequest requestCacheWrapperObject
                = new ContentCachingRequestWrapper(request);
        Map<String, String[]> parametersMap = requestCacheWrapperObject.getParameterMap();
        return parametersMap.entrySet().stream()
                .map(entry ->
                        entry.getKey() + "=" +
                                String.join(",",
                                        Arrays.asList(entry.getValue())
                                )
                )
                .collect(Collectors.joining("&"));
    }

}
