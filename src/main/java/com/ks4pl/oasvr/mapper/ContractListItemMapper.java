package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.ContractListItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ContractListItemMapper {
    ArrayList<ContractListItem> selectByCondition(Map<String, Object> condition);
    Integer total(Map<String, Object> condition);
}

