package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.UserListItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface UserListItemMapper {

    ArrayList<UserListItem> selectByCondition(HashMap<String, Object> conditon);
    Integer total(Map<String, Object> condition);

    UserListItem selectById(@Param("uid")Integer uid);
}
