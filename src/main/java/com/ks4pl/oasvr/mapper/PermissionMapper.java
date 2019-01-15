package com.ks4pl.oasvr.mapper;


import com.ks4pl.oasvr.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PermissionMapper {

    Permission selectPermissionByUser(@Param("uid")Integer uid);
    Integer insert(@Param("per")Permission permission);
    Integer update(@Param("per")Permission permission);
}
