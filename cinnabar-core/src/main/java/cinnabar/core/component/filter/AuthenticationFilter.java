package cinnabar.core.component.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import cinnabar.core.authentication.AuthenticationDao;
import cinnabar.core.authentication.dto.UserValidateDto;
import cinnabar.core.component.listener.StartupListener;
import cinnabar.core.component.redis.RedisHelper;
import cinnabar.core.context.Context;
import cinnabar.core.context.CookieContextWrapper;
import cinnabar.core.util.AuthenticationServiceURICollection;
import cinnabar.core.util.HttpUtils;
import cinnabar.core.util.MD5Encryptor;

/**
 * Common filter, for all APIs.
 * 
 * @author Vic.Chu
 *
 */
@WebFilter(filterName = "cinnabarFilter", urlPatterns = "/*")
@Order(1)
public class AuthenticationFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

	private static final String AUTH_RESULT_KEY = "authenticationResult";

	private static final String DO_LOGIN_URI = "/login.do";

	private static final String LOGIN_PAGE = "/login";

	private static final String HOME_PAGE = "/index";

	private static final String CONTEXT_NAME = "CONTEXT";

	private static final Long REDIS_THRESHOLD = 25 * 60 * 1000L;

	private static final String REMEMBER_ME = "rememberMe";

	private static final String STORED_URI = "STORED_URI";

	@Value(value = "cinnabar.env.name")
	private static String ENV_NAME;

	@Value(value = "cinnabar.cookie.domain")
	private static String DOMAIN;

	@Value(value = "cinnabar.app.name")
	private static String APP_NAME;

	@Value(value = "cinnabar.cookie.path")
	private static String COOKIE_PATH;

	private AuthenticationDao authenticationDao;

	private RedisHelper<String, Context> redisHelper;

	@Override
	public void destroy() {
		LOG.info("Authentication filter is being destroyed!");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse resp = (HttpServletResponse) arg1;
		HttpSession session = req.getSession();
		String sessionId = session.getId();
		Cookie contextCookie = HttpUtils.getCookieByName(req, CONTEXT_NAME, "", COOKIE_PATH);
		String URI = req.getRequestURI();
		String dispatchURI = HOME_PAGE;
		// if URI is for login
		if (DO_LOGIN_URI.equals(URI)) {
			try {
				String loginName = req.getParameter("loginName");
				String password = MD5Encryptor.getEncryptedString(req.getParameter("password"));
				UserValidateDto user = authenticationDao.validate(loginName, password);
				if (user != null) {
					Context cxt = new Context();
					Long currentTime = System.currentTimeMillis();
					cxt.setClientType(HttpUtils.getClientType(req));
					cxt.setIpAddress(HttpUtils.getIp(req));
					cxt.setLoginTime(currentTime);
					cxt.setRedisId(sessionId);
					boolean rememberMe = Boolean.valueOf(req.getParameter(REMEMBER_ME));
					cxt.setRememberMe(rememberMe);
					cxt.setUserId(user.getId());
					cxt.setUserRole(user.getUserRole());
					cxt.setRedisTime(currentTime);
					session.setAttribute(CONTEXT_NAME, cxt);
					String value = CookieContextWrapper.encryptContextJson(cxt.toString());
					HttpUtils.writeCookie(resp, CONTEXT_NAME, value, "", COOKIE_PATH);
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			HttpUtils.getCookieByName(req, STORED_URI, DOMAIN, COOKIE_PATH);
			String storedURI = req.getParameter(STORED_URI);
			if (storedURI != null) {
				dispatchURI = storedURI;
			}
			req.getRequestDispatcher(dispatchURI).forward(req, resp);
		} else {
			Context cxt = (Context) session.getAttribute(CONTEXT_NAME);
			Long currentTimeMillis = System.currentTimeMillis();
			if (cxt == null) {
				cxt = CookieContextWrapper.getContextFromCookie(contextCookie);
				if (cxt != null) { // has logged
					session.setAttribute(CONTEXT_NAME, cxt);
					if (currentTimeMillis - cxt.getRedisTime() >= REDIS_THRESHOLD) {
						// refresh redistime
						cxt.setRedisTime(currentTimeMillis);
						// rewrite to cookie
						String value = CookieContextWrapper.encryptContextJson(cxt.toString());
						HttpUtils.writeCookie(resp, CONTEXT_NAME, value, "", COOKIE_PATH);
						session.setAttribute(CONTEXT_NAME, cxt);
						redisHelper.opsForValue().set(CONTEXT_NAME, cxt);
					}
				} else {
					// if never logged
					if (!AuthenticationServiceURICollection.isAuthenticationURI(URI)) {
						chain.doFilter(req, resp);
					} else {
						req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
					}
				}
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		LOG.info("Authentication filter is being initialized");
		authenticationDao = StartupListener.getBeanByName("authenticationDao");
		redisHelper = StartupListener.getBeanByName("redisHelper");
	}

	private void returnError(String errorMsg, HttpServletResponse response) throws IOException {
		Map<String, String> result = Maps.newHashMap();
		result.put(AUTH_RESULT_KEY, errorMsg);
		ObjectMapper mapper = new ObjectMapper();
		try (PrintWriter writer = response.getWriter();) {
			writer.write(mapper.writeValueAsString(result));
		}
	}

}
