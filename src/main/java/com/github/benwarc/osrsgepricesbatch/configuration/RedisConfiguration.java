package com.github.benwarc.osrsgepricesbatch.configuration;

import com.github.benwarc.osrsgepricesbatch.dto.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfiguration {

    public static final String ITEM_CACHE = "item";
    public static final String DOUBLE_COLON = "::";

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return builder -> builder
                .withCacheConfiguration(ITEM_CACHE,
                        RedisCacheConfiguration
                                .defaultCacheConfig());
    }

    @Bean
    public LettuceConnectionFactory connectionFactory(@Value("${spring.data.redis.host}") String host,
                                                      @Value("${spring.data.redis.port}") int port) {

        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, Item> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Item> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new StringRedisSerializer());
        return template;
    }
}
