package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.ContractTemplateListItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ContractTemplateListItemMapper {
    ArrayList<ContractTemplateListItem> selectByCondition(Map<String, Object> condition);
    Integer total(Map<String, Object> condition);

    @Select("<script>" +
            "select c.name name, issueDate, c.state state, u.name operatorName, operatorId, operateTime " +
            "from contracttemplate c " +
            "join user u on operatorId=u.id " +
            "where 1=1 " +
            "<foreach collection='array' index='index' item='item' open='' separator='' close=''>" +
            "and (c.name like concat('%', #{item}, '%') " +
            "  or c.state like concat('%', #{item}, '%')) " +
            "</foreach> " +
            "</script>")
    ArrayList<ContractTemplateListItem> fuzzyQuery(String[] keys);
}

