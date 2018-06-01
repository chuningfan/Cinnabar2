package cinnabar.user.start;

import org.springframework.boot.SpringApplication;

import cinnabar.core.annotation.CinnabarService;

@CinnabarService(
applicationInjectionPackages={"cinnabar.core.*", "cinnabar.user.*"}, 
entityScanPackages="cinnabar.user.entity", 
jpaRepositoryPackages="cinnabar.user.repository",
servletComponentScanPackages="cinnabar.core.*")
public class UserServiceApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplicationStarter.class, args);
	}
	
}
