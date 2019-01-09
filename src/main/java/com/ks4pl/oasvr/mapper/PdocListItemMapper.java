package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.PdocListItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface PdocListItemMapper {
    ArrayList<PdocListItem> selectByCondition(Map<String, Object> condition);
    Integer total(Map<String, Object> con);
}
