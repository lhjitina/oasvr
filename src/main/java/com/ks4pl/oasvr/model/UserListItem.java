package com.ks4pl.oasvr.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
public class UserListItem {
    private Integer id;
    private String name;
    @NotNull(message = "tel can't be null")
    private String tel;
    @NotNull(message = "email can't be null")
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
    private Boolean perCon;

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
                ", perCon=" + perCon +
                '}';
    }
}
