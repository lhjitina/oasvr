package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface UserMapper {

    @Select({"select id, name, tel, email, departmentId,",
            "state, registTime, lastLoginTime",
            "from user",
            "where tel=#{te} OR email=#{te} AND deleted=0"})
    User selectUserByTelOrEmail(String te);

    @Select({"select id, name, tel, email, departmentId,",
            "state, registTime, lastLoginTime",
            "from user",
            "where id=#{uid}"})
    User slectUserById(Integer uid);

    @Insert("insert into user (name, tel, email, departmentId, state, registTime, passwd)" +
            "values(#{name}, #{tel}, #{email}, #{departmentId}, #{state}, #{registTime}, #{passwd})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer insert(User user);

    @Delete({"delete from user where id=#{uid}"})
    Integer deleteById(@Param("uid") Integer uid);

    @Update("<script>" +
            "UPDATE user <trim prefix='SET' suffixOverrides=','> " +
            "<if test='u.name!=null'> name=#{u.name}, </if> " +
            "<if test='u.tel != null'> tel=#{u.tel}, </if> " +
            "<if test='u.email != null'> email=#{u.email},</if>  " +
            "<if test='u.departmentId != null'> departmentId=#{u.departmentId},</if> " +
            "<if test='u.state != null'>  state=#{u.state},</if>" +
            "</trim>" +
            "WHERE id=#{u.id}" +
            "</script>")
    Integer updateById(@Param("u") User u);

    @Update("update user set passwd=#{passwd} where id=#{uid}")
    Integer updatePasswdById(@Param("uid")Integer uid, @Param("passwd")String passwd);
}
