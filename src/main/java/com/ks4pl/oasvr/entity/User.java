package com.ks4pl.oasvr.entity;

import com.ks4pl.oasvr.model.UserListItem;
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

    public User() {
    }

    public static User fromUserListItem(UserListItem userListItem){
        User user = new User();
        user.setId(userListItem.getId());
        user.setName(userListItem.getName());
        user.setDepartmentId(userListItem.getDepartmentId());
        user.setTel(userListItem.getTel());
        user.setEmail(userListItem.getEmail());
        user.setState(userListItem.getState());
        return user;
    }

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
