package cinnabar.core.component.redis;

import java.io.IOException;

import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * get object from redis
 * 
 * @author Vic.Chu
 *
 * @param <K>
 * @param <V>
 */
public class RedisHelper<K, V> extends RedisTemplate<K, V> {

	public V getObject(Object source, Class<V> clazz) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		if (source instanceof String) {
			return mapper.readValue(source.toString(), clazz);
		} else {
			byte[] byteArray = mapper.writeValueAsBytes(source);
			return mapper.readerFor(clazz).readValue(byteArray);
		}
	}
	
}
