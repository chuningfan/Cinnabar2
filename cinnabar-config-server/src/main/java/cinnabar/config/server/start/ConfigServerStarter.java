package cinnabar.config.server.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Config Server
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerStarter {
	
	// test url:http://localhost:8888/subservice/dev
	// test url2: http://localhost:8888/config-client/dev
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerStarter.class, args);
	}
}
