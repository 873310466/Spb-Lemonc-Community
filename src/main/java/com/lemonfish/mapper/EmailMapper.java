package com.lemonfish.mapper;

import com.lemonfish.entity.Email;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
  *  Mapper 接口
  *
  * @author LemonFish
  * @since 2020-05-24
  */
@Repository
@Mapper
public interface EmailMapper extends BaseMapper<Email> {

}
