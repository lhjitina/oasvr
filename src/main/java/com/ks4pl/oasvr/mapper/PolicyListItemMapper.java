package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.PolicyListItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface PolicyListItemMapper {

    ArrayList<PolicyListItem> selectByCondition(Map<String, Object> condition);
    Integer total(Map<String, Object> con);

    @Select("<script>" +
            "select p.name name, institution, issueDate, p.state state, u.name operatorName, operatorId, operateTime " +
            "from policy p " +
            "join user u on operatorId=u.id " +
            "where 1=1 " +
            "<foreach collection='array' index='index' item='item' open='' separator='' close=''>" +
            "and (p.name like concat('%', #{item}, '%') " +
            "  or institution like concat('%', #{item}, '%') " +
            "  or p.state like concat('%', #{item}, '%')) " +
            "</foreach> " +
            "</script>")
    ArrayList<PolicyListItem> fuzzyQuery(String[] keys);
}
