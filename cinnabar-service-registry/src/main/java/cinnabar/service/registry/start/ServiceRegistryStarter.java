package cinnabar.service.registry.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 子服务注册与发现
 * @author Vic.Chu
 *
 */
@EnableEurekaServer
@SpringBootApplication
public class ServiceRegistryStarter {
    
    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistryStarter.class, args);
    }
}
