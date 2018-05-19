package cinnabar.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableEurekaClient
@EnableDiscoveryClient
@EntityScan
@ServletComponentScan
@SpringBootApplication
@EnableJpaRepositories
public @interface Cinnabar {
	
	@AliasFor(annotation=EnableJpaRepositories.class, attribute="basePackages")
	String[] jpaRepositoryPackages() default {};
	
	
	@AliasFor(annotation=SpringBootApplication.class, attribute="scanBasePackages")
	String[] applicationInjectionPackages() default {"cinnabar.core.*"};
	
	
	@AliasFor(annotation=ServletComponentScan.class, attribute="basePackages")
	String[] servletComponentScanPackages() default {};
	
	@AliasFor(annotation=EntityScan.class, attribute="basePackages")
	String[] entityScanPackages() default {};
}
