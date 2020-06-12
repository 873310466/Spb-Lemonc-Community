package com.lemonfish.config.mybatis_plus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.config.mybatis_plus
 * @date 2020/4/30 12:48
 */
@Component
public class FilerFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdTime", Date.class, new Date()); // 起始版本 3.3.0(推荐使用)
        this.strictUpdateFill(metaObject, "updatedTime",  Date.class, new Date());//推荐使用)
        this.strictInsertFill(metaObject, "salt", String.class, "hahaha"); // 起始版本 3.3.0(推荐使用)
        System.out.println(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updatedTime",  Date.class, new Date());//推荐使用)
    }
}
