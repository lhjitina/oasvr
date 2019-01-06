package com.ks4pl.oasvr.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RespData {
    Integer code;
    String message;
    Object data;

    protected RespData _ok(Object data){
        this.code = RespCode.OK.getCode();
        this.message = RespCode.OK.getMsg();
        this.data = data;
        return this;
    }

    protected RespData _err(RespCode respCode){
        this.code = respCode.getCode();
        this.message = respCode.getMsg();
        this.data = null;
        return this;
    }

    public static RespData ok(Object data){
        return new RespData()._ok(data);
    }

    public static RespData ok(){
        return new RespData()._ok(null);
    }

    public static RespData err(RespCode respCode){
        return new RespData()._err(respCode);
    }

    public RespData setCode(RespCode respCode){
        this.code = respCode.getCode();
        this.message = respCode.getMsg();
        return this;
    }

    public RespData setMsg(String msg){
        this.message = msg;
        return this;
    }
    public Object getData(){
        return this.data;
    }
}
