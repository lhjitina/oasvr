package com.ks4pl.oasvr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter @Setter
public class ShareInfo {
    private Integer id;
    private String tag;
    @NotNull
    @NotBlank
    private String name;
    @NotNull @NotBlank
    private Integer operatorId;
    private Timestamp operateTime;

    @Override
    public String toString() {
        return "ShareInfo{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", name='" + name + '\'' +
                ", operatorId=" + operatorId +
                ", operateTime=" + operateTime +
                '}';
    }
}
