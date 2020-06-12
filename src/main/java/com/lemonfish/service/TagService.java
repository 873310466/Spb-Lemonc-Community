package com.lemonfish.service;

import com.lemonfish.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
  * <p>
  * 标签表 服务类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-07
  */
public interface TagService extends IService<Tag> {
    List<Tag> selectTagList();

    Tag selectTagById(Long id);

    int updateTag(Tag tag);

    int  deleteTagById(Long id);

}
