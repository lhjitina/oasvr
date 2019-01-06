package com.ks4pl.oasvr.dto;

public enum RespCode {
    OK(0, "ok"),
    NO_REGIST(1, "not regist"),
    NO_LOGIN(2, "not login"),
    NO_PERM(3, "no permission"),
    PASS_ERR(4, "passwd error"),
    SERV_ERR(5, "server error"),
    NO_TOKEN(6, "no token"),
    RELOGIN(7, "repeat login"),
    USR_SAME(8, "user has exist"),
    PARAM_ERR(9, "parameter error");

    private Integer code;
    private String msg;

    private RespCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(int code){
        for (RespCode c: RespCode.values()){
            if (c.code == code){
                return c.msg;
            }
        }
        return null;
    }

    public String getMsg(){
        return this.msg;
    }

    public Integer getCode() {
        return code;
    }
}
