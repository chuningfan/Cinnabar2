package cinnabar.core.component.rpc;

import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class BalancedRestTemplate extends RestTemplate {
	
	public BalancedRestTemplate(List<HttpMessageConverter<?>> messageConverters) {
		super(messageConverters);
	}
	
}
