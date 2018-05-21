package cinnabar.subservice.start;

import org.springframework.boot.SpringApplication;

import cinnabar.core.annotation.Cinnabar;

@Cinnabar(
applicationInjectionPackages={"cinnabar.core.*", "cinnabar.subservice.*"}, 
entityScanPackages="cinnabar.subservice.entity", 
jpaRepositoryPackages="cinnabar.subservice.dao",
servletComponentScanPackages="cinnabar.core.*")
public class SubserviceStarter {

	public static void main(String[] args) {
        SpringApplication.run(SubserviceStarter.class, args);
    }
	
}
