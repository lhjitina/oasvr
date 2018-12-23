package com.ks4pl.oasvr.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class User {
    private Integer id;
    private String name;
    private Integer departmentId;
    private String tel;
    private String email;
    private String state;
    private Timestamp registTime;
    private Timestamp lastLoginTime;
    private String passwd;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", departmentId=" + departmentId +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                ", registTime=" + registTime +
                ", lastLoginTime=" + lastLoginTime +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
