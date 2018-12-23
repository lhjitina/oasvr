package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.Summary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SummaryMapper {

    public Integer insert(@Param("sum")Summary sum);

    public Integer deleteByName(@Param("name")String name);
}
