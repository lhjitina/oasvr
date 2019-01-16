package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    User selectUserByTelOrEmail(String te);

    User slectUserById(Integer uid);

    @Insert("insert into user (name, tel, email, departmentId, state, registTime, passwd)" +
            "values(#{name}, #{tel}, #{email}, #{departmentId}, #{state}, #{registTime}, #{passwd})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
 //   @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", keyColumn = "id", before = false, resultType=Integer.class)
    Integer insert(User user);

    Integer deleteById(@Param("uid") Integer uid);

    Integer updateById(@Param("u") User u);

    Integer updatePasswdById(@Param("uid")Integer uid, @Param("passwd")String passwd);
}
