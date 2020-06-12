/*
package com.lemonfish.util;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

import java.nio.charset.Charset;

*/
/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.util
 * @date 2020/5/17 19:15
 *//*

public class AESUtil {
    public static final String SECRET_KEY = "LEMONCLEMONCLMZC";

    public static Long decryptStr(String idStr) {
        // 先进行url节码
        String idDecode = URLDecoder.decode(idStr, Charset.defaultCharset());
        AES aes = SecureUtil.aes(SECRET_KEY.getBytes());
        // 解密
        String idRes = aes.decryptStr(idDecode);
        return Long.parseLong(idRes);
    }
}
*/
