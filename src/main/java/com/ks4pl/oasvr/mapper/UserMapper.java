package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User selectUserByTelOrEmail(String te);

    User slectUserById(Integer uid);

    Integer insert(@Param("user") User user);

    Integer deleteById(@Param("uid") Integer uid);

    Integer updateById(@Param("u") User u);

    Integer updatePasswdById(@Param("uid")Integer uid, @Param("passwd")String passwd);

    Integer getLastInsertId();
}
