package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    public User selectUserByTelOrEmail(@Param("TelOrEmail") String nte);
}
