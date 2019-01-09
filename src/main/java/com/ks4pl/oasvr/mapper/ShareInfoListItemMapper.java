package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.PolicyListItem;
import com.ks4pl.oasvr.model.ShareInfoListItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;
@Mapper
public interface ShareInfoListItemMapper {
    ArrayList<ShareInfoListItem> selectByCondition(Map<String, Object> condition);
    Integer total(Map<String, Object> con);
}
