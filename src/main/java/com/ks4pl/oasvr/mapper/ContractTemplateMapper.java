package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.ContractTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContractTemplateMapper {
    Integer insertone(@Param("con") ContractTemplate contractTemplate);
    Integer updateStateByName(@Param("con") ContractTemplate contractTemplate);
}
