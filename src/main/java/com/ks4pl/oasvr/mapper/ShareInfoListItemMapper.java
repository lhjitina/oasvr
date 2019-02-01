package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.PolicyListItem;
import com.ks4pl.oasvr.model.ShareInfoListItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.Map;
@Mapper
public interface ShareInfoListItemMapper {
    ArrayList<ShareInfoListItem> selectByCondition(Map<String, Object> condition);
    Integer total(Map<String, Object> con);

    @Select("<script>" +
            "select s.name, s.tag, s.operatorId, s.operateTime, u.name operatorName " +
            "from shareinfo s " +
            "join user u on u.id = s.operatorId " +
            "where 1=1 " +
            "<foreach collection='array' index='index' item='item' open='' separator='' close=''>" +
            "and (s.name like concat('%', #{item}, '%') or s.tag like concat('%', #{item}, '%')) " +
            "</foreach>" +
            "</script>")
    ArrayList<ShareInfoListItem> fuzzyQuery(String[] keys);
}
