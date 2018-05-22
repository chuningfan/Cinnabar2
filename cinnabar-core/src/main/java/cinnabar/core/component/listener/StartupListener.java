package cinnabar.core.component.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cinnabar.core.util.AuthenticationServiceURICollection;

/**
 * Implicit listener
 * 
 * for providing Spring beans
 * 
 * @author Vic.Chu
 *
 */
@WebListener("ServiceStartupListener")
public class StartupListener implements ServletContextListener, ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(StartupListener.class);
	
	private static ApplicationContext CXT;
	
	@Value("cinnabar.auth.uris")
	private String authenticationURIs;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		LOG.info("StartupListener has been destroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		String[] uris = authenticationURIs.split(",");
		if (!ArrayUtils.isEmpty(uris)) {
			AuthenticationServiceURICollection.setAll(uris);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CXT = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBeanByName(String beanName) {
		return (T) CXT.getBean(beanName);
	}
	
	public static <T> T getBeanByClass(Class<T> clazz) {
		return CXT.getBean(clazz);
	}
	
	public static <T> T getBeanByClassAndParams(Class<T> clazz, Object...arg1) {
		return CXT.getBean(clazz, arg1);
	}
}
