package com.ks4pl.oasvr.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespPage extends RespData {
    Integer num;
    Integer size;
    Integer total;

    public RespPage setPage(Integer num, Integer size, Integer total){
        this.num = num;
        this.size = size;
        this.total = total;
        return this;
    }

//    public RespPage setPage(Integer num, Integer size){
//        this.num = num;
//        this.size = size;
//        return this;
//    }
//
//    public static RespPage okPage(Object data){
//        RespPage respPage = new RespPage();
//        respPage._ok(data);
//        return respPage;
//    }

    public static RespPage okPage(Integer num, Integer size, Integer total, Object data){
        RespPage respPage = new RespPage();
        respPage._ok(data);
        respPage.setPage(num, size, total);
        return respPage;
    }

    public static RespPage errPage(RespCode code){
        RespPage respPage = new RespPage();
        respPage._err(code);
        return respPage;
    }
}
