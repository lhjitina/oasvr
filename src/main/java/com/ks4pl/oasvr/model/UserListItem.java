package com.ks4pl.oasvr.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
public class UserListItem {
    private Integer id;

    @NotNull @NotBlank
    private String name;

    @NotNull(message = "tel can't be null")
    @NotBlank(message = "tel can't be blank")
    private String tel;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Integer departmentId;
    private String departmentName;

    @NotNull @NotBlank
    private String state;

    private Timestamp registTime;
    private Timestamp lastLoginTime;
    private Boolean perPol;
    private Boolean perReg;
    private Boolean perSum;
    private Boolean perUsr;
    private Boolean perCon;
    private Boolean perDoc;
    private Boolean perCw;
    private Boolean perCr;


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
                ", perDoc=" + perDoc +
                '}';
    }

}
