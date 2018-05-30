package com.quartz.quartz.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.quartz.quartz.entity.QuartzTaskEntity;

@Mapper
public interface QuartzTaskMapper extends BaseMapper<QuartzTaskEntity> {

}
