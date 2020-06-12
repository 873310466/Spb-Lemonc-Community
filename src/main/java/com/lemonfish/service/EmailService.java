package com.lemonfish.service;

import com.lemonfish.entity.Email;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
  * <p>
  *  服务类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-24
  */
public interface EmailService extends IService<Email> {


    boolean sendEmail(Email email);
}
