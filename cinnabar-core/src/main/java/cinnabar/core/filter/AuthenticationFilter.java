package cinnabar.core.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import cinnabar.core.authentication.AuthenticationDao;
import cinnabar.core.authentication.dto.UserValidateDto;
import cinnabar.core.context.Context;
import cinnabar.core.context.ContextWrapper;
import cinnabar.core.listener.StartupListener;
import cinnabar.core.redis.RedisHelper;
import cinnabar.core.util.DateTimeUtils;
import cinnabar.core.util.HttpUtils;

@WebFilter(filterName="cinnabarFilter", urlPatterns="/*")
public class AuthenticationFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	private static final String AUTH_RESULT_KEY = "authenticationResult";
	
	private static final String DATE_FORMAT = "YYYY-MM-DD HH:mm:ss";
	
	private static final String DO_LOGIN_URI = "/login.do";
	
	private static final String LOGIN_PAGE = "/login";
	
	private static final String HOME_PAGE = "/index";
	
	private static final String COOKIE_NAME = "CONTEXT";
	
	private static final String REMEMBER_ME = "rememberMe";
	
	@Value(value = "cinnabar.env.name")
	private static String ENV_NAME;
	
	@Value(value = "cinnabar.domain.name")
	private static String DOMAIN;
	
	@Value(value = "cinnabar.app.name")
	private static String APP_NAME;
	
	@Value(value="cinnabar.context.services")
	private static String CONTEXT_SERVICES;
	
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
			DOMAIN = "/";
		}
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String contextName = COOKIE_NAME + "_" + ENV_NAME + "_" + APP_NAME;
		String requestURI = request.getRequestURI();
		Cookie cookie = HttpUtils.getCookieByName(request, contextName, DOMAIN);
		String rememberMe = request.getParameter(REMEMBER_ME);
		Integer expireTime = 30 * 60;
		if (DO_LOGIN_URI.equals(requestURI) && cookie == null) {
			// do validation
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			UserValidateDto dto = authenticationDao.validate(userName, password);
			if (dto != null) {
				Context cxt = new Context();
				String redisId = dto.getRedisId();
				cxt.setRedisId(redisId);
				cxt.setIpAddress(HttpUtils.getIp(request));
				cxt.setLoggedTime(DateTimeUtils.getSimpleDateFormat(DATE_FORMAT).format(new Date()));
				cxt.setClientType(HttpUtils.getClientType(request));
				cxt.setUserId(dto.getId());
				cxt.setRememberMe(rememberMe);
				try {
					cookie = ContextWrapper.getCookie(cxt, requestURI, DOMAIN);
				} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
						| NoSuchAlgorithmException | NoSuchPaddingException e) {
					e.printStackTrace();
					LOG.error("When getting cookie from context, occurred an error! " + e.getMessage());
				}	
				switch(dto.getUserStatus()) {
				case ACTIVE:
					// create context
					if (Boolean.valueOf(rememberMe)) {
						//save cookie for 7 days
						expireTime = 60 * 60 * 24 * 7;
						cookie.setMaxAge(expireTime);
					}
					redisHelper.opsForValue().set(redisId, cxt, expireTime, TimeUnit.SECONDS);
					response.addCookie(cookie);
					break;
				case DEACTIVE:
					returnError("DEACTIVE", response);
					return;
				case LOCKED:
					returnError("LOCKED", response);
					return;
				default:
					LOG.error("Unknow user status!!!");
					return;
				}
			} else {
				returnError("NOT_FOUND", response);
				return;
			}
		}
		// --------------------------------request not from login---------------------------------
		Context context = null;
		if (cookie != null) {
			try {
				context = ContextWrapper.decryptCookie(cookie.getValue());
			} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
					| NoSuchPaddingException e) {
				LOG.error("When getting context from cookie, occurred an error! " + e.getMessage());
				e.printStackTrace();
			}
			context = redisHelper.opsForValue().get(context.getRedisId());
			if (context != null) {
				if (!Boolean.valueOf(context.getRememberMe())) {
					//refresh expire time
					redisHelper.opsForValue().set(context.getRedisId().toString(), context, expireTime, TimeUnit.SECONDS);
				}
			}
		}
		request.setAttribute("context", context);
		if (LOGIN_PAGE.equals(requestURI) && context != null) {
			request.getRequestDispatcher(HOME_PAGE).forward(request, response);
			return;
		}
		arg2.doFilter(request, response);
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
