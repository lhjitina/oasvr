package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.RegulationListItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Mapper
public interface RegulationListItemMapper {
    ArrayList<RegulationListItem> selectByCondition(Map<String, Object> conditionMap);
    Integer total(HashMap<String, Object> con);

}
