package cinnabar.core.component.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 
 * Spring bean for redis operations
 * 
 * @author Vic.Chu
 *
 */
@Configuration  
@ConditionalOnClass(RedisOperations.class)  
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

	
	@Bean  
    @ConditionalOnMissingBean(name = "redisHelper")  
    public RedisHelper<Object, Object> redisHelper(  
            RedisConnectionFactory redisConnectionFactory) {  
		RedisHelper<Object, Object> template = new RedisHelper<>();  
        Jackson2JsonRedisSerializer<?> redisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setValueSerializer(redisSerializer);  
        template.setHashValueSerializer(redisSerializer);  
        template.setKeySerializer(new StringRedisSerializer());  
        template.setHashKeySerializer(new StringRedisSerializer());  
        template.setConnectionFactory(redisConnectionFactory);  
        return template;  
    }
	
	@Bean  
    @ConditionalOnMissingBean(name="stringRedisHelper")  
    public StringRedisHelper stringRedisHelper(  
            RedisConnectionFactory redisConnectionFactory) {  
		StringRedisHelper template = new StringRedisHelper();  
        template.setConnectionFactory(redisConnectionFactory);  
        return template;  
    }
}
