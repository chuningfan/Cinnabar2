package cinnabar.core.redis;

import java.io.IOException;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringRedisHelper extends StringRedisTemplate {

	public <T> T getObjectFromString(String source, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isEmpty(source) || source.trim().length() == 0) {
			return null;
		}
		return mapper.readValue(source.getBytes(), clazz);
	}
	
}
