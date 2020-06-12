package com.lemonfish.controller;
import com.alibaba.fastjson.JSON;
import com.lemonfish.entity.Tag;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.mapper.TagMapper;
import com.lemonfish.service.TagService;
import com.lemonfish.util.MyJsonResult;
import com.lemonfish.util.TreeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
  *
  * @author LemonFish
  * @since 2020-05-07
  */
@RestController
@RequestMapping("/public/tag")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public MyJsonResult getTags()  {
        List<Tag> tagsList = tagService.selectTagList();
        return tagsList.size() > 0 ? MyJsonResult.success(TreeUtils.buildTree(tagsList)) : MyJsonResult.fail(CodeEnum.FAIL_NOT_FOUND);
    }
}
