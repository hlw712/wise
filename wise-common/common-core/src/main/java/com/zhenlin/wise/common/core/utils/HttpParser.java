package com.zhenlin.wise.common.core.utils;

import java.util.Objects;

/**
 * Http header解析器
 *
 * @author huanglinwei
 * @date 2023/9/19 10:45
 * @version PM-3124
 */
public class HttpParser {

    public static String getIpAddress(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = request.getHeader("remoteip");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("x-forwarded-for");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        // 如果有多个ip，截取前面ip地址
        if (ip != null && ip.contains(",")) {
            String[] ips = ip.split(",");
            ip = ips[0];
        }
        return ip;
    }
}
