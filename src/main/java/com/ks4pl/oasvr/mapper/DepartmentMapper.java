package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface DepartmentMapper {

    public ArrayList<Department> selectAll();
    public Department selectByName(@Param("name") String name);
    public Department selectById(@Param("id") Integer id);
}
