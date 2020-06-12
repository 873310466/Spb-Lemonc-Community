package com.lemonfish.controller;
import com.lemonfish.entity.User;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.service.UserService;
import com.lemonfish.util.MyJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
  *
  * @author LemonFish
  * @since 2020-05-06
  */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfoByToken")
    public MyJsonResult getUserInfoByToken(@RequestParam("token") String token){
        User userInfo = userService.getUserInfoByToken(token);
        return userInfo != null ? MyJsonResult.success(userInfo) : MyJsonResult.fail(CodeEnum.FAIL_INVALID_TOKEN);
    }

    @GetMapping("/{uid}")
    public MyJsonResult getUserInfoById(@PathVariable("uid") Long uid){
        User userInfo = userService.getUserInfoById(uid);
        return userInfo != null ? MyJsonResult.success(userInfo) : MyJsonResult.fail(CodeEnum.FAIL_INVALID_TOKEN);
    }

    @PutMapping("/")
    public MyJsonResult updateUserInfo(@RequestBody User user) {
        boolean res = userService.updateUserInfo(user);
        return res?MyJsonResult.success():MyJsonResult.fail(CodeEnum.USERINFO_DUMPLICATION);
    }
}
