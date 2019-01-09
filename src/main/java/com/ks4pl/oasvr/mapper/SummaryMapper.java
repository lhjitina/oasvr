package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.Summary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SummaryMapper {

    Integer insert(@Param("sum")Summary sum);

    Integer deleteByName(@Param("name")String name);
}
