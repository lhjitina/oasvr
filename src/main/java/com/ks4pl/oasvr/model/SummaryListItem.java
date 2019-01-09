package com.ks4pl.oasvr.model;

import com.ks4pl.oasvr.entity.Summary;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class SummaryListItem {
    private Integer id;
    @NotBlank @NotNull
    private String name;
    @NotBlank @NotNull
    private Date meetingDate;
    @NotNull
    private Integer operatorId;
    private Timestamp operateTime;
    private String operatorName;



    public SummaryListItem() {

    }

    public SummaryListItem(Integer id, String name, Date meetingDate, Integer operatorId, Timestamp operateTime, String operatorName) {
        this.id = id;
        this.name = name;
        this.meetingDate = meetingDate;
        this.operatorId = operatorId;
        this.operateTime = operateTime;
        this.operatorName = operatorName;
    }
}
