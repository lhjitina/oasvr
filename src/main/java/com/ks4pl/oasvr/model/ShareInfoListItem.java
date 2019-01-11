package com.ks4pl.oasvr.model;

import com.ks4pl.oasvr.entity.ShareInfo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShareInfoListItem extends ShareInfo {
    String operatorName;

    @Override
    public String toString() {
        return "ShareInfoListItem{" +
                "operatorName='" + operatorName + '\'' +
                "} " + super.toString();
    }
}
