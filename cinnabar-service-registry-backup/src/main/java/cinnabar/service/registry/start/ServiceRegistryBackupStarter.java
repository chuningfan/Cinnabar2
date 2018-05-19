package cinnabar.service.registry.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServiceRegistryBackupStarter {
    
    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistryBackupStarter.class, args);
    }
}
