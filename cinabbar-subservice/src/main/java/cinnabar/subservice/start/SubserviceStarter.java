package cinnabar.subservice.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient // 该service将会注册在Eureka服务上，可以发现其他服务
@EnableDiscoveryClient // 可以被其他服务发现
@EntityScan(basePackages={"cinnabar.*.entity"}) //指定entity package， 以便hibernate 做映射
@ServletComponentScan(basePackages={"cinnabar"}) //扫描指定package，管理filter servlet listener 等
@SpringBootApplication(scanBasePackages={"cinnabar"}) //扫描指定package，spring DI 生效
@EnableJpaRepositories(basePackages={"cinnabar.*.dao"}) //开启JPA支持，指定package，该package下的repository（DAO）全部继承JPARepository， spring统一管理
public class SubserviceStarter {

	public static void main(String[] args) {
        SpringApplication.run(SubserviceStarter.class, args);
    }
	
	/**
	 * 注入resttemplate, 其他地方直接autowired调用，for RPC（REST）
	 * @return
	 */
	@Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
}
