package com.lemonfish.util;

import org.apache.commons.lang3.text.StrTokenizer;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * IpV4 获取真实ip地址
 */
public class IPUtil {

    public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    public static final Pattern PATTERN = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$");

    /**
     * String类型ip转为Long类型
     *
     * @param longIp
     * @return String
     */
    public static String longToIpV4(long longIp) {
        int octet3 = (int) ((longIp >> 24) % 256);
        int octet2 = (int) ((longIp >> 16) % 256);
        int octet1 = (int) ((longIp >> 8) % 256);
        int octet0 = (int) ((longIp) % 256);
        return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
    }

    /**
     * Long类型ip转为String类型
     *
     * @param ip
     * @return Long
     */
    public static long ipV4ToLong(String ip) {
        String[] octets = ip.split("\\.");
        return (Long.parseLong(octets[0]) << 24) + (Integer.parseInt(octets[1]) << 16)
                + (Integer.parseInt(octets[2]) << 8) + Integer.parseInt(octets[3]);
    }

    /**
     * @param ip
     * @return boolean
     */
    public static boolean isIPv4Private(String ip) {
        long longIp = ipV4ToLong(ip);
        return (longIp >= ipV4ToLong("10.0.0.0") && longIp <= ipV4ToLong("10.255.255.255"))
                || (longIp >= ipV4ToLong("172.16.0.0") && longIp <= ipV4ToLong("172.31.255.255"))
                || longIp >= ipV4ToLong("192.168.0.0") && longIp <= ipV4ToLong("192.168.255.255");
    }

    public static boolean isIPv4Valid(String ip) {
        return PATTERN.matcher(ip).matches();
    }

    /**
     * 获取String类型真实ip地址，基于反向代理。
     *
     * @param request
     * @return 在反向代理中将X-Forward-For替换为remote_addr，即，真实的IP地址。
     */
    public static String getIpFromRequest(HttpServletRequest request) {
        String ip;
        boolean found = false;
        if ((ip = request.getHeader("x-forwarded-for")) != null) {
            StrTokenizer tokenizer = new StrTokenizer(ip, ",");
            while (tokenizer.hasNext()) {
                ip = tokenizer.nextToken().trim();
                if (isIPv4Valid(ip) && !isIPv4Private(ip)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            // 获得ip地址
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
