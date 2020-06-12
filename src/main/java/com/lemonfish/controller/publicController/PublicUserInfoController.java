package com.lemonfish.controller.publicController;

import com.lemonfish.dto.UserAchiDTO;
import com.lemonfish.dto.UserListDTO;
import com.lemonfish.entity.User;
import com.lemonfish.service.UserService;
import com.lemonfish.util.MyJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.controller.publicController
 * @date 2020/5/16 9:52
 */
@RestController
@RequestMapping("/public/user")
public class PublicUserInfoController {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public MyJsonResult getUserInfoById(@PathVariable("id") Long id) {
        User user = userService.getUserInfoById(id);
        // 错误逻辑在service里面，所以能到这边肯定是success
        return MyJsonResult.success(user);
    }

    /**
     * 获取作者成就数据
     */
    @GetMapping("/achievement")
    public MyJsonResult getAchievement(@RequestParam(value = "id") Long id) {
        UserAchiDTO userAchiDTO = userService.getAchievement(id);
        return MyJsonResult.success(userAchiDTO);
    }


    /**
     * 获取浏览榜数据
     */
    @GetMapping("/viewList")
    public MyJsonResult getViewList() {
        List<UserListDTO> userListDTOList = userService.getViewList();
        return MyJsonResult.success(userListDTOList);
    }


    /**
     * 获取点赞榜数据
     */
    @GetMapping("/likeList")
    public MyJsonResult getLikeList() {
        List<UserListDTO> userListDTOList = userService.getLikeList();
        return MyJsonResult.success(userListDTOList);
    }

    /**
     * 获取点赞榜数据
     */
    @GetMapping("/collectionList")
    public MyJsonResult getCollectionList() {
        List<UserListDTO> userListDTOList = userService.getCollectionList();
        return MyJsonResult.success(userListDTOList);
    }
}
