package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.ShareInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ShareInfoMapper {
    @Insert("insert into shareinfo (name, tag, operatorId, operateTime)" +
            "values(#{si.name}, #{si.tag}, #{si.operatorId}, #{si.operateTime})" )
    Integer insert(@Param("si")ShareInfo si);

    @Update("update shareinfo set tag=#{si.tag}, operatorId=#{si.operatorId}, operateTime=#{si.operateTime}" +
            "where name=#{si.name}")
    Integer updateByName(@Param("si")ShareInfo si);

    @Delete("delete from shareinfo where name=#{name}")
    Integer deleteByName(String name);
}

