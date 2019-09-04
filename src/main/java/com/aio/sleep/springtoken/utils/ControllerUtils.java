package com.aio.sleep.springtoken.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

public class ControllerUtils {


    /**
     * 将对象以json形式返回
     * @param response
     *@param  object
     **/
    public static void writeObjectJsonResponse(HttpServletResponse response, Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(object);
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().println(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取请求ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {return null;}
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("HTTP_CLIENT_IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            }catch (UnknownHostException e){

            }
        }
        return ip;
    }

    /**
     * 获取随机数组成的salt值
     * **/
    public static String createSalt(int theLength) {
        String theChars = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
        ArrayList<Character> list = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < theLength; i++) {
            char c = theChars.charAt(r.nextInt(theChars.length()));
            list.add(c);
        }
        StringBuffer builder = new StringBuffer(list.size());
        for (Character ch:list){
            builder.append(ch);
        }
        return builder.toString();
    }

    /**
     * md5 加盐加密
     * **/
    public static String createMd5(String s, String salt) {
        String base = s.concat(salt);
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
