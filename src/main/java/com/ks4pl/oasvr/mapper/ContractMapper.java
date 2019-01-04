package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.Contract;
import com.ks4pl.oasvr.entity.Policy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContractMapper {
    Integer insertone(@Param("con") Contract contract);
    Integer updateStateByName(@Param("con") Contract contract);
}
