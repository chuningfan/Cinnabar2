package cinnabar.service.zuul.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import cinnabar.service.zuul.filter.SimpleFilter;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ServiceZuulStarter {

	public static void main(String[] args) {
		SpringApplication.run(ServiceZuulStarter.class, args);
	}

	@Bean
	public SimpleFilter simpleFilter() {
		return new SimpleFilter();
	}

}
