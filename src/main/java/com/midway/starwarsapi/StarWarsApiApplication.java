package com.midway.starwarsapi;

import com.midway.starwarsapi.util.ApiExpiration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@SpringBootApplication
@EnableCaching
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

/*
    @Configuration
    @EnableCaching
    public static class CachingConfig {

        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("addresses");
        }
    }

    @Component
    public static class SimpleCacheCustomizer
            implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

        @Override
        public void customize(ConcurrentMapCacheManager cacheManager) {
            cacheManager.setCacheNames(List.of("films-rest", "people-rest", "planets-rest"));
        }
    }
*/
/*
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("films-rest",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration("people-rest",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)))
                .withCacheConfiguration("planets-rest",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
    }
*/

/*
    @Configuration
    @EnableCaching
    public static class RedisConfiguration {
        @Value("${spring.redis.host}")
        private String REDIS_HOSTNAME;

        @Value("${spring.redis.port}")
        private int REDIS_PORT;

        @Bean
        public RedisTemplate<String, String> redisTemplate() {
            final RedisTemplate<String, String> redisTemplate =
                    new RedisTemplate<>();

            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

            redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(String.class));
            redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(REDIS_HOSTNAME, REDIS_PORT);

            JedisConnectionFactory factory =
                    new JedisConnectionFactory(configuration, JedisClientConfiguration.builder().build());

            factory.afterPropertiesSet();

            redisTemplate.setConnectionFactory(factory);

            return redisTemplate;
        }
    }
*/
/*
    @Configuration
    @ConfigurationProperties(prefix = "spring.redis")
    @Setter
    public class RedisConfig {

        private String host;
        private String password;

        @Bean
        @Primary
        public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(RedisConfiguration defaultRedisConfig) {
            LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                    .useSsl().build();
            return new LettuceConnectionFactory(defaultRedisConfig, clientConfig);
        }

        @Bean
        public RedisConfiguration defaultRedisConfig() {
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
            config.setHostName(host);
            config.setPassword(RedisPassword.of(password));
            return config;
        }
    }

 */
}
