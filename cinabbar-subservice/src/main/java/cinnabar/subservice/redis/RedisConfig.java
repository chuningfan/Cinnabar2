package cinnabar.subservice.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 手动设置自动配置的bean
 * redisTemplate & stringRedisTemplate
 * @author Vic.Chu
 *
 */
@Configuration  
@ConditionalOnClass(RedisOperations.class)  
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {
	
	/**
	 * return bean 并且为bean装载序列化器
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean  
    @ConditionalOnMissingBean(name = "cinnabarRedisHelper")  
    public CinnabarRedisHelper<Object, Object> cinnabarRedisHelper(  
            RedisConnectionFactory redisConnectionFactory) {  
		CinnabarRedisHelper<Object, Object> template = new CinnabarRedisHelper<>();  
        Jackson2JsonRedisSerializer<?> redisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setValueSerializer(redisSerializer);  
        template.setHashValueSerializer(redisSerializer);  
        template.setKeySerializer(new StringRedisSerializer());  
        template.setHashKeySerializer(new StringRedisSerializer());  
        template.setConnectionFactory(redisConnectionFactory);  
        return template;  
    }  
  
    @Bean  
    @ConditionalOnMissingBean(StringRedisTemplate.class)  
    public StringRedisTemplate stringRedisTemplate(  
            RedisConnectionFactory redisConnectionFactory) {  
        StringRedisTemplate template = new StringRedisTemplate();  
        template.setConnectionFactory(redisConnectionFactory);  
        return template;  
    }  
	
}
