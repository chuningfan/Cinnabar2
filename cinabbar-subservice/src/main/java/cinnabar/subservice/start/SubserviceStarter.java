package cinnabar.subservice.start;

import org.springframework.boot.SpringApplication;

import cinnabar.core.annotation.Cinnabar;

@Cinnabar(applicationInjectionPackages={"${cinnabar.core.component.package}", "cinnabar.subservice.*"}, 
entityScanPackages="cinnabar.subservice.entity", 
jpaRepositoryPackages="cinnabar.subservice.dao")
public class SubserviceStarter {

	public static void main(String[] args) {
        SpringApplication.run(SubserviceStarter.class, args);
    }
	
}
