package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.Contract;
import com.ks4pl.oasvr.model.ContractUpdate;
import org.apache.ibatis.annotations.*;

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

    @Select("<script>" +
            "select c.name name, partner, type, digest, start, end, autoRenewal, u.name operatorName " +
            "from contract c " +
            "join user u on operatorId=u.id " +
            "<where> 1=1 " +
            "<if test='name!=null'> and c.name like concat('%', #{name}, '%') </if>" +
            "<if test='partner!=null'> and partner like concat('%', #{partner}, '%')</if>" +
            "<if test='type!=null'> and type like concat('%', #{type}, '%')</if>" +
            "<if test='startDate!=null'> and end &gt; #{startDate}</if>" +
            "<if test='endDate!=null'> and end &lt; #{endDate}</if>" +
            "</where>" +
            "order by end" +
            "</script>")
    ArrayList<Contract> selectByCondition(Map<String, Object> con);

    @Select("<script>" +
            "select count(id)" +
            "from contract " +
            "<where> 1=1 " +
            "<if test='name!=null'> and name like concat('%', #{name}, '%') </if>" +
            "<if test='partner!=null'> and partner like concat('%', #{partner}, '%')</if>" +
            "<if test='type!=null'> and type like concat('%', #{type}, '%')</if>" +
            "<if test='startDate!=null'> and end &gt; #{startDate}</if>" +
            "<if test='endDate!=null'> and end &lt; #{endDate}</if>" +
            "</where>" +
            "</script>")
    Integer total(Map<String, Object> con);

    @Delete("delete from contract " +
            "where name=#{name}")
    Integer deleteCon(String name);

    @Update("update contract " +
            "set partner=#{partner}," +
            "type=#{type}," +
            "digest=#{digest}," +
            "start=#{start}," +
            "end=#{end}," +
            "autoRenewal=#{autoRenewal}," +
            "operatorId=#{operatorId}," +
            "operateTime=#{operateTime} " +
            "where name=#{name}")
    Integer updateCon(ContractUpdate contractUpdate);

    @Select("<script>" +
            "select c.name name, partner, type, digest, start, end, autoRenewal, u.name operatorName " +
            "from contract c " +
            "join user u on operatorId=u.id " +
            "where 1=1 " +
            "<foreach collection='array' index='index' item='item' open='' separator='' close=''>" +
            "and (c.name like concat('%', #{item}, '%') " +
            "  or partner like concat('%', #{item}, '%') " +
            "  or type like concat('%', #{item}, '%') " +
            "  or digest like concat('%', #{item}, '%')) " +
            "</foreach> " +
            "</script>")
    ArrayList<Contract> fuzzyQuery(String[] keys);
}
