package com.ks4pl.oasvr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespData {
    Integer code;
    String message;
    Object data;

    public static RespData ok(Object data){
        RespData respData = new RespData();
        respData.code = RespCode.OK.getCode();
        respData.message = RespCode.OK.getMsg();
        System.out.println("resp code:" + respData.code);
        respData.data = data;
        return respData;
    }

    public static RespData ok(){
        RespData respData = new RespData();
        respData.code = RespCode.OK.getCode();
        respData.message = RespCode.OK.getMsg();
        System.out.println("resp code:" + respData.code);
        respData.data = null;
        return respData;
    }

    public static RespData err(RespCode respCode){
        RespData respData = new RespData();
        respData.code = respCode.getCode();
        respData.message = respCode.getMsg();
        System.out.println("resp code:" + respData.code);
        respData.data = null;
        return respData;
    }

    public void setCode(RespCode respCode){
        this.code = respCode.getCode();
        this.message = respCode.getMsg();
    }

    public Object getData(){
        return this.data;
    }
}
