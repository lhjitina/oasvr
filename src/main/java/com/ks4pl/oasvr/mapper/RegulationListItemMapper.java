package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.RegulationListItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;


@Mapper
public interface RegulationListItemMapper {

    public ArrayList<RegulationListItem> selectAll();

}
