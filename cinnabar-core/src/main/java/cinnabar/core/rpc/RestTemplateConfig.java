package cinnabar.core.rpc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration  
public class RestTemplateConfig {

	@Bean
    @LoadBalanced
    @ConditionalOnMissingBean(name = "BalancedRestTemplate") 
	BalancedRestTemplate balancedRestTemplate() {
        return new BalancedRestTemplate();
    }
	
	@Bean
    @ConditionalOnMissingBean(name = "restTemplate") 
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
}
