package com.lemonfish.util;

import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.exception.BaseException;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.util
 * @date 2020/5/8 19:22
 */
public class ValidateUtils {
    public static void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new BaseException(CodeEnum.FAIL_INVALID_PARAM);
        }
    }

    public static void V_AIDandUID(Long aid, Long uid) {
        if (aid <= 0 || uid <= 0) {
            throw new BaseException(CodeEnum.FAIL_INVALID_PARAM);
        }
    }

    public static void V_COLLECTION(Collection c) {
        if (CollectionUtils.isEmpty(c)) {
            throw new BaseException(CodeEnum.FAIL_INVALID_PARAM);
        }
    }

    public static void V_PAGE(Long curPage, Long size) {
        if (curPage <= 0 || size <= 0) {
            throw new BaseException(CodeEnum.FAIL_PAGE_PARAMS);
        }
    }
}
