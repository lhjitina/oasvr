package com.ks4pl.oasvr.mapper;


import com.ks4pl.oasvr.entity.Permission;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PermissionMapper {

    @Select("select uid, pol, reg, sum, doc, con, usr, cw, cr " +
            "from permission " +
            "where uid=#{uid}")
    Permission selectPermissionByUser(Integer uid);

    @Insert("insert into permission " +
            "(uid, pol, reg, sum, doc, con, usr, cw, cr) " +
            "values(#{uid}, #{pol}, #{reg}, #{sum}, #{doc}, #{con}, #{usr}, #{cw}, #{cr})")
    Integer insert(Permission permission);

    @Update("update permission " +
            "set pol=#{pol}, reg=#{reg}, sum=#{sum}, doc=#{doc}, " +
            "con=#{con}, usr=#{usr}, cw=#{cw}, cr=#{cr} " +
            "where uid=#{uid}")
    Integer update(Permission permission);
}
