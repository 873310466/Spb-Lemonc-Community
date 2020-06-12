package com.lemonfish.controller.publicController;
import com.lemonfish.entity.Email;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.service.EmailService;
import com.lemonfish.util.MyJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
  *
  * @author LemonFish
  * @since 2020-05-24
  */
@RestController
@RequestMapping("/public/email")
public class EmailController {

    @Autowired
    private EmailService emailService;


    @PostMapping("/")
    public MyJsonResult sendEmail(@RequestBody Email email) {
        boolean res = emailService.sendEmail(email);
        return res ? MyJsonResult.success() : MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }

}
