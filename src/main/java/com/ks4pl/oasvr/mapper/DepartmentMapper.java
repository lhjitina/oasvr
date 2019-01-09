package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface DepartmentMapper {

    ArrayList<Department> selectByCondition(Map<String, Object> con);
    Department selectById(@Param("id") Integer id);
    Integer total(Map<String, Object> con);
}
