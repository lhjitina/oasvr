package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.Contract;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ContractMapper {

    @Insert("insert into contract " +
            "(name, partner, type, start, end, digest, operatorId, operateTime, autoRenewal) " +
            "values(#{name}, #{partner}, #{type}, #{start}, #{end}, #{digest}, " +
            "#{operatorId}, #{operateTime}, #{autoRenewal})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insertCon(Contract contract);

    ArrayList<Contract> selectByCondition(Map<String, Object> con);

    Integer total(Map<String, Object> con);

    @Delete("delete from contract " +
            "where name=#{name}")
    Integer deleteCon(String name);
}
