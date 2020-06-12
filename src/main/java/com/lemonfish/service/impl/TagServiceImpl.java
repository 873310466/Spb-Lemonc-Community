package com.lemonfish.service.impl;


import com.lemonfish.entity.Tag;
import com.lemonfish.mapper.TagMapper;
import com.lemonfish.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

/**
  * <p>
  * 标签表 服务实现类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-07
  */
@Service
@Transactional
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    @Autowired
    private TagMapper tagMapper;

    /**
     * @Description 获取所有的Tag
     *
     * @return List<Tag>
     */
    @Override
    @Cacheable("getAllTags")
    public List<Tag> selectTagList(){
        return tagMapper.selectList(null);
    }

    /**
     * @Description 获取指定ID的Tag
     *
     * @return Tag
     */
    @Override
    public Tag selectTagById(Long id){
        if(id<=0){
            return null;
        }
        return tagMapper.selectById(id);
    }

    /**
     * @Description 更新Tag
     *
     * @param tag
     * @return int , > 0 means success, or not
     */
    @Override
    public int updateTag(Tag tag){
        return tagMapper.updateById(tag);
    }

    /**
     * @Description 删除指定ID的Tag
     *
     * @param id
     * @return int , > 0 means success, or not
     */
    @Override
    public int deleteTagById(Long id){
        return tagMapper.deleteById(id);
    }


}


