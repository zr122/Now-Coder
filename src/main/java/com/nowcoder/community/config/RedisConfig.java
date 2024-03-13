//package com.nowcoder.community.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//@Configuration
//public class RedisConfig {
////    @Bean
////    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
////        RedisTemplate<String, Object> template = new RedisTemplate<>();
////        template.setConnectionFactory(factory);
////
////        //设置key的序列化方式
////        template.setKeySerializer(RedisSerializer.string());
////        //设置value的序列化方式
////        template.setValueSerializer(RedisSerializer.json());
////        //设置hash的key的序列化方式
////        template.setHashKeySerializer(RedisSerializer.string());
////        //设置hash的value的序列化方式
////        template.setHashValueSerializer(RedisSerializer.json());
////
////        template.afterPropertiesSet();
////        return template;
////
////
////    }
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//
//        // 设置key的序列化方式
//        template.setKeySerializer(RedisSerializer.string());
//        // 设置value的序列化方式，添加 jackson-datatype-jsr310 模块
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        template.setValueSerializer(RedisSerializer.json());
//        // 设置hash的key的序列化方式
//        template.setHashKeySerializer(RedisSerializer.string());
//        // 设置hash的value的序列化方式，添加 jackson-datatype-jsr310 模块
//        template.setHashValueSerializer(RedisSerializer.json());
//
//        template.afterPropertiesSet();
//        return template;
//    }
//
//}
package com.nowcoder.community.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        // 设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());

        // 设置value和hash的value的序列化方式
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        RedisSerializer<Object> jsonSerializer = RedisSerializer.json();

        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
