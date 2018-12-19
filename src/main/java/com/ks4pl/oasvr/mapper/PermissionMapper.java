package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface PermissionMapper {

    public ArrayList<Department> selectPermissionDepartmentByUser(@Param("userId") Integer userId);
}
