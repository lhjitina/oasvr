package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.PolicyListItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface PolicyListItemMapper {

    ArrayList<PolicyListItem> selectByCondition(Map<String, Object> condition);
}
