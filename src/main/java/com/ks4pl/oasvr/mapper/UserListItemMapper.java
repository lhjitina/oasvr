package com.ks4pl.oasvr.mapper;

import com.ks4pl.oasvr.model.UserListItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface UserListItemMapper {

    public ArrayList<UserListItem> selectByCondition(Map<String, Object> conditon);
}
