package cinnabar.core.component.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import cinnabar.core.authentication.AuthenticationDao;
import cinnabar.core.authentication.dto.UserValidateDto;
import cinnabar.core.component.listener.StartupListener;
import cinnabar.core.component.pool.ContextPool;
import cinnabar.core.component.redis.RedisHelper;
import cinnabar.core.constant.UserStatus;
import cinnabar.core.context.Context;
import cinnabar.core.context.CookieContextWrapper;
import cinnabar.core.util.AuthenticationServiceURICollection;
import cinnabar.core.util.DateTimeUtils;
import cinnabar.core.util.HttpUtils;

/**
 * Common filter, for all APIs.
 * 
 * @author Vic.Chu
 *
 */
@WebFilter(filterName="cinnabarFilter", urlPatterns="/*")
@Order(1)
public class AuthenticationFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	private static final String AUTH_RESULT_KEY = "authenticationResult";
	
	private static final String DATE_FORMAT = "YYYY-MM-DD HH:mm:ss";
	
	private static final String DO_LOGIN_URI = "";
	
	private static final String LOGIN_PAGE = "/login";
	
	private static final String HOME_PAGE = "/index";
	
	private static final String COOKIE_NAME = "CONTEXT";
	
	private static final String REMEMBER_ME = "rememberMe";
	
	private static final String STORED_URI_KEY = "storedUri";
	
	@Value(value = "cinnabar.env.name")
	private static String ENV_NAME;
	
	@Value(value = "cinnabar.cookie.domain")
	private static String DOMAIN;
	
	@Value(value = "cinnabar.app.name")
	private static String APP_NAME;
	
	@Value(value="cinnabar.cookie.path")
	private static String COOKIE_PATH;
	
	private AuthenticationDao authenticationDao;
	
	private RedisHelper<String, Context> redisHelper;
	
	@Override
	public void destroy() {
		LOG.info("Authentication filter is being destroyed!");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		if (DOMAIN == null) {
			DOMAIN = "";
		}
		if (COOKIE_PATH == null) {
			COOKIE_PATH = "/";
		}
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String contextName = COOKIE_NAME + "_" + ENV_NAME + "_" + APP_NAME;
		String requestURI = request.getRequestURI();
		Cookie cookie = HttpUtils.getCookieByName(request, contextName, DOMAIN);
		String rememberMe = request.getParameter(REMEMBER_ME);
		Integer expireTime = 30 * 60;
		Context context = null;
		// if current request is for login and no cookie data
		Cookie uriCookie = null;
		if (DO_LOGIN_URI.equals(requestURI) && cookie == null) {
			// do validation
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			// query data from DB with user name and password
			UserValidateDto dto = authenticationDao.validate(userName, password);
			// if found
			if (dto != null) {
				switch(dto.getUserStatus() == null ? UserStatus.UNKNOWN : dto.getUserStatus()) {
				case ACTIVE: // if user is available, create a context for this user
					context = new Context();
					String redisId = dto.getRedisId();
					context.setRedisId(redisId);
					context.setIpAddress(HttpUtils.getIp(request));
					context.setLoggedTime(DateTimeUtils.getSimpleDateFormat(DATE_FORMAT).format(new Date()));
					context.setClientType(HttpUtils.getClientType(request));
					context.setUserId(dto.getId());
					context.setRememberMe(rememberMe);
					try {
						// create cookie by context
						cookie = CookieContextWrapper.getCookie(context, contextName, DOMAIN, COOKIE_PATH);
					} catch (Exception e) {
						LOG.error("When getting cookie from context, occurred an error! " + e.getMessage());
						e.printStackTrace();
					}	
					// see if this login is [remember me]
					if (Boolean.valueOf(rememberMe)) {
						//save cookie for 7 days
						expireTime = 60 * 60 * 24 * 7;
						cookie.setMaxAge(expireTime);
					}
					// put this context to redis
					redisHelper.opsForValue().set(redisId, context, expireTime, TimeUnit.SECONDS);
					// write cookie to browser
					response.addCookie(cookie);
					uriCookie = HttpUtils.getCookieByName(request, STORED_URI_KEY, DOMAIN);
					if (uriCookie != null) {
						String URI = uriCookie.getValue();
						request.getRequestDispatcher(URI).forward(request, response);
					} else {
						request.getRequestDispatcher(HOME_PAGE).forward(request, response);
					}
				case DEACTIVE:
					returnError("DEACTIVE", response);
					return;
				case LOCKED:
					returnError("LOCKED", response);
					return;
				default:
					LOG.error("Unknow user status!!!");
					returnError("UNKNOWN", response);
					return;
				}
			} else {
				returnError("NOT_FOUND", response);
				return;
			}
		}
		// --------------------------------if request not from login---------------------------------
		// if cookie is not null
		if (cookie != null) {
			try {
				// get context by cookie
				context = CookieContextWrapper.decryptCookie(cookie.getValue());
			} catch (Exception e) {
				LOG.error("When getting context from cookie, occurred an error! " + e.getMessage());
				e.printStackTrace();
			}
			// get context from redis by redis ID
			context = redisHelper.opsForValue().get(context.getRedisId());
			// if we could get one
			if (context != null) {
				// if this it's a [remember me] context, we will not refresh the time, or refresh time
				if (!Boolean.valueOf(context.getRememberMe())) {
					//refresh expire time
					redisHelper.opsForValue().set(context.getRedisId().toString(), context, expireTime, TimeUnit.SECONDS);
				}
			}
			if (AuthenticationServiceURICollection.isAuthenticationURI(requestURI) && context == null) {
				Cookie setUriCookie = new Cookie(STORED_URI_KEY, requestURI);
				setUriCookie.setPath(COOKIE_PATH);
				setUriCookie.setDomain(DOMAIN);
				response.addCookie(setUriCookie);
				request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
			}
		} else {
			// if no cookie and current URI needs authentication, store the URI and go to login page
			if (AuthenticationServiceURICollection.isAuthenticationURI(requestURI)) {
				Cookie setUriCookie = new Cookie(STORED_URI_KEY, requestURI);
				setUriCookie.setPath(COOKIE_PATH);
				setUriCookie.setDomain(DOMAIN);
				response.addCookie(setUriCookie);
				request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
			}
		}
		// set context for current thread
		ContextPool.setContext(context);
		// if the request is to the login page, because of it has a valid cookie, go to home page
		if (LOGIN_PAGE.equals(requestURI) && context != null) {
			request.getRequestDispatcher(HOME_PAGE).forward(request, response);
			return;
		}
		arg2.doFilter(request, response);
		ContextPool.destroy();
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
		try(PrintWriter writer = response.getWriter();) {
			writer.write(mapper.writeValueAsString(result));
		}
	}
	
}
