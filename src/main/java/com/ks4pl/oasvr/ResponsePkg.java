package com.ks4pl.oasvr;

public class ResponsePkg {
    private Integer code;
    private String descripition;

    public ResponsePkg() {
        code = 0;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescripition() {
        return descripition;
    }

    public void setDescripition(String descripition) {
        this.descripition = descripition;
    }
}
