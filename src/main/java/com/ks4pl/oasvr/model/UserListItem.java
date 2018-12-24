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
    private Boolean perPol;
    private Boolean perReg;
    private Boolean perSum;
    private Boolean perUsr;

    @Override
    public String toString() {
        return "UserListItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", state='" + state + '\'' +
                ", registTime=" + registTime +
                ", lastLoginTime=" + lastLoginTime +
                ", perPol=" + perPol +
                ", perReg=" + perReg +
                ", perSum=" + perSum +
                ", perUsr=" + perUsr +
                '}';
    }
}
