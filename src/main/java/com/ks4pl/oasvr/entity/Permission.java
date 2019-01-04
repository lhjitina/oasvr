package com.ks4pl.oasvr.entity;

import com.ks4pl.oasvr.model.UserListItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permission {
    private Integer uid;
    private Integer pol;
    private Integer reg;
    private Integer sum;
    private Integer doc;
    private Integer usr;
    private Integer con;

    public Permission() {
    }

    public static Permission fromUserListItem(UserListItem userListItem){
        Permission p = new Permission();
        p.setUid(userListItem.getId());
        p.setPol(userListItem.getPerPol()? 1 : 0);
        p.setReg(userListItem.getPerReg()? 1 : 0);
        p.setSum(userListItem.getPerSum()? 1 : 0);
        p.setUsr(userListItem.getPerUsr()? 1 : 0);
        p.setDoc(userListItem.getPerPol()? 1 : 0);
        p.setCon(userListItem.getPerCon()? 1 : 0);
        return p;
    }
}
