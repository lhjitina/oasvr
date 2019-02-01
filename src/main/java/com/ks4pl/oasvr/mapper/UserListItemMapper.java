package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.UserListItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface UserListItemMapper {

    @Select("<script>" +
            "select u.id id, u.name name, u.tel tel, u.email email, u.departmentId departmentId, " +
            "u.state state, u.registTime registTime, u.lastLoginTime lastLoginTime, " +
            "d.name departmentName, " +
            "p.pol perPol, p.reg perReg, p.sum perSum, p.usr perUsr, p.con perCon, p.doc perDoc " +
            "from user u " +
            "join department d on d.id=u.departmentId " +
            "join permission p on p.uid = u.id " +
            "<where> 1=1 " +
            "<if test='name!=null'> and u.name like concat(&#39;%&#39;, #{name}, &#39;%&#39;) </if> " +
            "<if test='tel!=null'> and u.tel like concat(&#39;%&#39;, #{tel}, &#39;%&#39;) </if>" +
            "<if test='email!=null'> and u.email like concat(&#39;%&#39;, #{email}, &#39;%&#39;) </if>" +
            "<if test='state!=null'> and u.state=#{state} </if> " +
            "<if test='departmentId!=null'> and u.departmentId=#{departmentId} </if>" +
            "</where> " +
            "</script>")
    ArrayList<UserListItem> selectByCondition(Map<String, Object> conditon);

    @Select("<script>" +
            "select count(u.id) " +
            "from user u " +
            "join department d on d.id=u.departmentId " +
            "join permission p on p.uid = u.id " +
            "<where> 1=1 " +
            "<if test='name!=null'> and u.name like concat(&#39;%&#39;, #{name}, &#39;%&#39;) </if> " +
            "<if test='tel!=null'> and u.tel like concat(&#39;%&#39;, #{tel}, &#39;%&#39;) </if>" +
            "<if test='email!=null'> and u.email like concat(&#39;%&#39;, #{email}, &#39;%&#39;) </if>" +
            "<if test='state!=null'> and u.state=#{state} </if> " +
            "<if test='departmentId!=null'> and u.departmentId=#{departmentId} </if>" +
            "</where> " +
            "</script>")
    Integer total(Map<String, Object> condition);

    @Select("select u.id id, u.name name, u.tel tel, u.email email, u.departmentId departmentId, " +
            "u.state state, u.registTime registTime, u.lastLoginTime lastLoginTime, " +
            "d.name departmentName, " +
            "p.pol perPol, p.reg perReg, p.sum perSum, p.usr perUsr, p.con perCon, p.doc perDoc, p.cw perCw, p.cr perCr " +
            "from user u " +
            "join department d on d.id=u.departmentId " +
            "join permission p on p.uid = u.id " +
            "where u.id=#{uid} ")
    UserListItem selectById(Integer uid);

    @Select("<script>" +
            "select u.id id, u.name name, u.tel tel, u.email email, u.departmentId departmentId, " +
            "u.state state, u.registTime registTime, u.lastLoginTime lastLoginTime, " +
            "d.name departmentName, " +
            "p.pol perPol, p.reg perReg, p.sum perSum, p.usr perUsr, p.con perCon, p.doc perDoc " +
            "from user u " +
            "join department d on d.id=u.departmentId " +
            "join permission p on p.uid = u.id " +
            "where 1=1 " +
            "<foreach collection='array' index='index' item='item' open='' separator='' close=''>" +
            "and (u.name like concat('%', #{item}, '%') " +
            "  or u.tel like concat('%', #{item}, '%') " +
            "  or u.email like concat('%', #{item}, '%') " +
            "  or d.name like concat('%', #{item}, '%')) " +
            "</foreach> " +
            "</script>")
    ArrayList<UserListItem> fuzzyQuery(String[] keys);
}


