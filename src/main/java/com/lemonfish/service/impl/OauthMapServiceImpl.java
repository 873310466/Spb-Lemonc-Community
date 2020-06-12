package com.lemonfish.service.impl;


import com.lemonfish.entity.OauthMap;
import com.lemonfish.mapper.OauthMapMapper;
import com.lemonfish.service.OauthMapService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
/**
  * <p>
  *  服务实现类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-16
  */
@Service
@Transactional
public class OauthMapServiceImpl extends ServiceImpl<OauthMapMapper, OauthMap> implements OauthMapService {


    @Autowired
    private OauthMapMapper oauthMapMapper;


}


