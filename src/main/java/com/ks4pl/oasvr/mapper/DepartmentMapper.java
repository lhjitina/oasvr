package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface DepartmentMapper {

    ArrayList<Department> selectByCondition(HashMap<String, Object> con);
    Department selectById(@Param("id") Integer id);
    Integer total(HashMap<String, Object> con);
}
