package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.Regulation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface RegulationMapper {

    public ArrayList<Regulation> selectAll();

    public int insert(@Param("regulation") Regulation regulation);
}
