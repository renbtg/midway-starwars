package com.midway.starwarsapi;

import com.midway.starwarsapi.util.ApiExpiration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.Duration;
import java.util.Collections;

@SpringBootApplication
public class StarWarsApiApplication implements ApiExpiration {

    @Value("${appVersion}")
    private String appVersion;
    @Value("${appBuildNumber}")
    private String appBuildNumber;

    public static void main(String[] args) {
//        ApiExpiration.checkApiExpiration();
        SpringApplication.run(StarWarsApiApplication.class, args);
    }


    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("Midway Starwars API")
                        .description("API para teste na Midway, processo seletivo")
                        .version("v"+appVersion+"."+appBuildNumber)
                        .contact(new Contact()
                                .name("Renato Battaglia")
                                .email("renbtg@hotmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Roots Technologies e Consultoria LTDA"));
    }
/*
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.second-datasource")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

 */

    @Value("${cache.film.duration.minutes}")
    private Integer cacheDurationMinutes;

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        "TODO - nao estah sendo chamado, cache desabilitado";
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(cacheDurationMinutes))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        "TODO - nao estah sendo chamado, cache desabilitado";
        return (builder) -> builder
                .withCacheConfiguration("films-rest",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(cacheDurationMinutes)))
                .withCacheConfiguration("people-rest",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(cacheDurationMinutes)))
                .withCacheConfiguration("planets-rest",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(cacheDurationMinutes)));
    }

}
