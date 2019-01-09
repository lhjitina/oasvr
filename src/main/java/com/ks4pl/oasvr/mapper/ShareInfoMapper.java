package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.ShareInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ShareInfoMapper {
    @Insert("insert into shareinfo (name, operatorId, operateTime " +
            "values(#{name}, #{operatorId}, #{operateTime}" )
    Integer insert(@Param("si")ShareInfo si);

    @Update("update shareinfo set name=#{name}, operatorId=#{operatorId}, operateTime=#{operateTime}" +
            "where name=#{name}")
    Integer updateByName(@Param("si")ShareInfo si);

    @Delete("delete from shareinfo where name=#{name}")
    Integer deleteByName(String name);
}

