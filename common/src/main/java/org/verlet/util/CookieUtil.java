package org.verlet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    private static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    public static void addCookie(HttpServletResponse response,String name,String value,Integer maxAge,String path) {
        try {
            value= URLEncoder.encode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void delCookie(HttpServletRequest request,HttpServletResponse response,String name){
        Map<String, Cookie> cookieMap = readCookieMap(request);
        Cookie cookie = cookieMap.get(name);
        if(cookie!=null){
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    public static Map<String,Cookie> readCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<>();
        if (request.getCookies()==null){
            return null;
        }
        for(Cookie cookie :request.getCookies()){
            cookieMap.put(cookie.getName(),cookie);
        }
        return cookieMap;
    }

}
