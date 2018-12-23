package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.Policy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PolicyMapper {

    Integer insert(@Param("pol")Policy policy);
    Integer updateStateByName(@Param("pol")Policy policy);
}
