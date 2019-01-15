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

    public Permission(){}

    public Permission(Integer uid) {
        this.uid = uid;
        this.con = 0;
        this.doc = 0;
        this.pol = 0;
        this.reg = 0;
        this.sum = 0;
        this.usr = 0;

    }

    public static Permission fromUserListItem(UserListItem userListItem){
        Permission p = new Permission(userListItem.getId());
        if (userListItem.getPerPol() != null){
            p.setPol(userListItem.getPerPol()? 1 : 0);
        }
        if (userListItem.getPerReg() != null){
            p.setReg(userListItem.getPerReg()? 1 : 0);
        }
        if (userListItem.getPerSum() != null){
            p.setSum(userListItem.getPerSum()? 1 : 0);
        }
        if (userListItem.getPerDoc() != null){
            p.setDoc(userListItem.getPerDoc()? 1 : 0);
        }
        if (userListItem.getPerUsr() != null){
            p.setUsr(userListItem.getPerUsr()? 1 : 0);
        }
        if (userListItem.getPerCon() != null){
            p.setCon(userListItem.getPerCon()? 1 : 0);
        }
        return p;
    }
}
