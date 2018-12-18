package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.RegulationListItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface RegulationListItemMapper {

    public ArrayList<RegulationListItem> selectAll();
    public Map<String, Object> selectContent(@Param("name") String name);
}
