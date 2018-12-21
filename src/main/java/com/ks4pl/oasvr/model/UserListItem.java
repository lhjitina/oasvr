package com.ks4pl.oasvr.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
public class UserListItem {
    private Integer id;
    private String name;
    private String tel;
    private String email;
    private Integer departmentId;
    private String departmentName;
    private String state;
    private Timestamp registTime;
    private Timestamp lastLoginTime;
}
