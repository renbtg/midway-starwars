package com.midway.starwarsapi.controller;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GeneralMVCConfig implements WebMvcConfigurer {
    private static final Logger logger = LogManager.getLogger(GeneralMVCConfig.class);

    @Autowired
    GeneralRequestInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return (tomcat) -> tomcat.addConnectorCustomizers((connector) -> {
            if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?> protocolHandler) {
                protocolHandler.setUseKeepAliveResponseHeader(false);
            }
        });
    }

}
