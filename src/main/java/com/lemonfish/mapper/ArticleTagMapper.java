package com.lemonfish.mapper;

import com.lemonfish.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *   * 文章与标签的中间表 Mapper 接口
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-07
 *  
 */
@Repository
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    boolean saveOrUpdateBatch(List<ArticleTag> list);

}
