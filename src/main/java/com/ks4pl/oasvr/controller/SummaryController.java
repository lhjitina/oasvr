package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.entity.Policy;
import com.ks4pl.oasvr.entity.Summary;
import com.ks4pl.oasvr.model.PolicyListItem;
import com.ks4pl.oasvr.model.SummaryListItem;
import com.ks4pl.oasvr.service.PermissionService;
import com.ks4pl.oasvr.service.SessionService;
import com.ks4pl.oasvr.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SummaryController {

    @Autowired
    private SummaryService summaryService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/api/front/summary/list", method = RequestMethod.GET)
    public ArrayList<SummaryListItem> frontGetSummaryList(String name,
                                                        String startDate,
                                                        String endDate){
        System.out.println("enter: /api/front/summary/list");
        return getSummaryList(name, startDate, endDate);
    }

    @RequestMapping(value = "/api/console/summary/list", method = RequestMethod.GET)
    public ArrayList<SummaryListItem> consoleGetPolicyList(String name,
                                                          String startDate,
                                                          String endDate){
        System.out.println("enter:/api/console/summary/list");

        return getSummaryList(name, startDate, endDate);
    }

    private ArrayList<SummaryListItem> getSummaryList(String name, String startDate, String endDate){
        Map<String, Object> condition = new HashMap<>();
        if (name !=null && !name.trim().isEmpty()){
            condition.put("name", name);
        }
        if (startDate != null && !startDate.trim().isEmpty()){
            condition.put("startDate", startDate);
        }
        if (endDate != null && !endDate.trim().isEmpty()){
            condition.put("endDate", endDate);
        }
        System.out.println("conditons:"+condition);
        ArrayList<SummaryListItem> ret = summaryService.selectByCondition(condition);
        if (ret == null){
            System.out.println("select summary return value is null");
        }
        return ret;
    }

    @RequestMapping(value="/api/summary/content/{name}", method = RequestMethod.GET)
    public void getSummaryContent(@PathVariable String name, HttpServletResponse response){
        if (summaryService.getSummaryContent(name, response) == false){
            System.out.println("getSummaryContent error");
        }
    }

    @RequestMapping(value = "/api/summary/upload", method = RequestMethod.POST)
    public String FileUpload(@RequestParam(value = "meetingDate") String meetingDateStr,
                             MultipartFile file){

        System.out.println("upload file with meetingDate=" + meetingDateStr);

        //检查用户是否登录
        if (sessionService.getCurrentUserId() == 0){
            System.out.println("no login");
            return "no login";
        }

        //检查用户权限
        if (!permissionService.sumPermissionExist(sessionService.getCurrentUserId())){
            System.out.println("no permission");
            return "no permission";
        }


        //存储文件
        if (!summaryService.FileUpload(file)){
            System.out.println("save file fail");
            return "fail";
        }

        //转换并检查会议日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date meetingDate = null;
        try {
            meetingDate = sdf.parse(meetingDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "fail";
        }

        //存入数据库
        Summary summary = new Summary();
        summary.setName(file.getOriginalFilename());
        summary.setMeetingDate(meetingDate);
        summary.setOperatorId(sessionService.getCurrentUserId());
        summary.setOperateTime(new Timestamp(System.currentTimeMillis()));

        if (summaryService.insert(summary) == 0){
            System.out.println("save to database fail");
            return "fail";
        }
        return "ok";
    }

    @RequestMapping(value = "/api/summary/delete", method = RequestMethod.GET)
    public void deleteSummary(@RequestParam("name")String name) {
        System.out.println("deleteSummary:" + name);
        summaryService.deleteByName(name);
    }

}
