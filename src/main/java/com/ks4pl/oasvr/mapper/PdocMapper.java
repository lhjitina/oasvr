package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.PartnerDoc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PdocMapper {

    Integer insert(@Param("pdoc") PartnerDoc partnerDoc);
    Integer deleteByPartnerAndName(@Param("p")String p, @Param("n")String name);

}
