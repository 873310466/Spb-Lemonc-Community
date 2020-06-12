package com.lemonfish.service.impl;


import com.lemonfish.entity.Email;
import com.lemonfish.mapper.EmailMapper;
import com.lemonfish.service.EmailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemonfish.util.MailUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
  * <p>
  *  服务实现类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-24
  */
@Service
@Transactional
public class EmailServiceImpl extends ServiceImpl<EmailMapper, Email> implements EmailService {

    public static final String DEFAULT_ADVISER = "匿名的小伙伴";

    @Autowired
    private EmailMapper emailMapper;
    @Autowired
    MailUtil mailUtil;

    @Override
    public boolean sendEmail(Email email) {
        if ("".equals(email.getAdviser())) { email.setAdviser(DEFAULT_ADVISER); }

        mailUtil.sendMail(
                "873310466@qq.com",
                "来自“课程表”的留言~",
                "<h1 >From : " + email.getAdviser() + "</h1> " + "<h3 style='color:skyblue;'>Content : " + email.getContent() + "</h3>");

        // 保存到数据库中
        int insert = emailMapper.insert(email);
        return insert>0;
    }
}


