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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * To resolve the complex annotations on Spring boot starter.
 * collect most of common used annotations
 * @author Vic.Chu
 *
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableEurekaClient
@EnableDiscoveryClient
@EntityScan
@ServletComponentScan
@SpringBootApplication
@EnableJpaRepositories
@ComponentScan
public @interface CinnabarService {
	
	// field for JPA
	@AliasFor(annotation=EnableJpaRepositories.class, attribute="basePackages")
	String[] jpaRepositoryPackages() default {};
	
	// field for DI
	@AliasFor(annotation=SpringBootApplication.class, attribute="scanBasePackages")
	String[] applicationInjectionPackages() default {"cinnabar.core.component.*"};
	
	// field for components like servlet listener filter ...
	@AliasFor(annotation=ServletComponentScan.class, attribute="basePackages")
	String[] servletComponentScanPackages() default {"cinnabar.core.component.*"};
	
	// field for ORM entity
	@AliasFor(annotation=EntityScan.class, attribute="basePackages")
	String[] entityScanPackages() default {};
	
	// field for components of Spring
	@AliasFor(annotation=ComponentScan.class, attribute="basePackages")
	String[] componentScanPackages() default{"cinnabar.core.component.*"};
}
