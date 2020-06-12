package com.lemonfish.mapper;

import com.lemonfish.entity.Collection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
  * 收藏集表 Mapper 接口
  *
  * @author LemonFish
  * @since 2020-05-13
  */
@Repository
@Mapper
public interface CollectionMapper extends BaseMapper<Collection> {
    /**
     * 增加count
     * @param cids
     * @return
     */
    boolean incrCount(@Param("set") Set<Long> cids);

    /**
     * 减少count
     * @param cids
     * @return
     */
    boolean decrCount(@Param("set") Set<Long> cids);

    /**
     * 减少count
     * @return
     */
    @Update("update collection set count = count - #{count} where id = #{cid}")
    boolean decrOneCount(@Param("cid") Long cid,
                         @Param("count") Integer count);

    /**
     * 获取乐观锁字段
     */
    @Select("select version from collection where id = #{id}")
    int getVersion(Long id);
}
