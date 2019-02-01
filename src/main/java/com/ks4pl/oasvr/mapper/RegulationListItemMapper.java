package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.RegulationListItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Mapper
public interface RegulationListItemMapper {
    ArrayList<RegulationListItem> selectByCondition(Map<String, Object> conditionMap);
    Integer total(Map<String, Object> con);

    @Select("<script>" +
            "select r.name name, issueDate, r.state state, operateTime, " +
            "       d.name departmentName, r.departmentId departmentId,  " +
            "       u.name operatorName, operatorId " +
            "from regulation r " +
            "join user u on operatorId=u.id " +
            "join department d on r.departmentId=d.id " +
            "where 1=1 " +
            "<foreach collection='array' index='index' item='item' open='' separator='' close=''>" +
            "and (r.name like concat('%', #{item}, '%') " +
            "  or d.name like concat('%', #{item}, '%') " +
            "  or r.state like concat('%', #{item}, '%')) " +
            "</foreach> " +
            "</script>")
    ArrayList<RegulationListItem> fuzzyQuery(String[] keys);
}
