package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.SummaryListItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface SummaryListItemMapper {

    ArrayList<SummaryListItem> selectByCondition(Map<String, Object> condition);
    Integer total(Map<String, Object> con);

    @Select("<script>" +
            "select s.name name, meetingDate, operateTime, " +
            "       u.name operatorName, operatorId " +
            "from summary s " +
            "join user u on operatorId=u.id " +
            "where 1=1 " +
            "<foreach collection='array' index='index' item='item' open='' separator='' close=''>" +
            "and (s.name like concat('%', #{item}, '%')) " +
            "</foreach> " +
            "</script>")
    ArrayList<SummaryListItem> fuzzyQuery(String[] keys);
}
