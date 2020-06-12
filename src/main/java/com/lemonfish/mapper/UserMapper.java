package com.lemonfish.mapper;

import com.lemonfish.dto.UserAchiDTO;
import com.lemonfish.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 *   *  Mapper 接口
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-06
 *  
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 更新用户头像的URL
     * @param url
     * @param uid
     */
    @Update("update user set avatar_url = #{url} where id = #{uid}")
    void updateUserAvatarUrl(@Param("url") String url,
                             @Param("uid") Long uid);


    /**
     * 计算用户的文章成就值
     */
    UserAchiDTO getUserAchievement(@Param("uid") Long uid);

    /**
     * 计算所有用户的文章成就值
     */
    List<UserAchiDTO> getAllUserAchievement(Set<Long> usersId);

    /**
     * 获取乐观锁字段
     */
    @Select("select version from user where id = #{id}")
    int getVersion(Long id);
}
