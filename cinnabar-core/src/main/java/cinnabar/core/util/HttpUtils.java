package cinnabar.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
	private static final String UNKNOWN_IP = "unknown";
	
	public static Cookie getCookieByName(HttpServletRequest request, String keyName, String domain) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0) {
			return null;
		}
		if (StringUtils.isBlank(keyName)) {
			return null;
		}
		if (domain == null) {
			domain = "";
		}
		for (Cookie c: cookies) {
			if (domain.equals(c.getDomain()) && keyName.equals(c.getName())) {
				return c;
			}
		}
		return null;
	}
	
	public static String getIp(HttpServletRequest request) {
		LOG.debug("*** HttpUtil.class Starting getIp() ***");
        String ip = request.getHeader("X-Cluster-Client-Ip");
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
        	LOG.debug("header is X-Forwarded-For");
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
        	LOG.debug("header is Proxy-Client-IP");
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
        	LOG.debug("header is WL-Proxy-Client-IP");
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
        	LOG.debug("header is HTTP_CLIENT_IP");
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
        	LOG.debug("header is HTTP_X_FORWARDED_FOR");
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isBlank(ip)) {
            return UNKNOWN_IP;
        }
        return ip;
    }
	
	public static String getClientType(HttpServletRequest request) {
		return request.getParameter("clientType");
    }
	
}
