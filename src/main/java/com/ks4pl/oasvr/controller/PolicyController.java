package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.entity.Policy;
import com.ks4pl.oasvr.entity.Regulation;
import com.ks4pl.oasvr.model.PolicyListItem;
import com.ks4pl.oasvr.model.RegulationListItem;
import com.ks4pl.oasvr.service.PermissionService;
import com.ks4pl.oasvr.service.PolicyService;
import com.ks4pl.oasvr.service.SessionService;
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
public class PolicyController {

    @Autowired
    private PolicyService policyService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/api/front/policy/list", method = RequestMethod.GET)
    public ArrayList<PolicyListItem> frontGetPolicyList(String name,
                                                                String institution,
                                                                String startDate,
                                                                String endDate,
                                                                String state) {
        System.out.println("enter:   /api/front/policy/list");
        return getPolicyList(name, institution, startDate, endDate, state);
    }

    @RequestMapping(value = "/api/console/policy/list", method = RequestMethod.GET)
    public ArrayList<PolicyListItem> consoleGetPolicyList(String name,
                                                          String institution,
                                                          String startDate,
                                                          String endDate,
                                                          String state){
        System.out.println("enter:/api/console/policy/list  institution=" + institution);

        return getPolicyList(name, institution, startDate, endDate, state);
    }

    private ArrayList<PolicyListItem> getPolicyList(String name, String institution, String startDate, String endDate, String state){
        Map<String, Object> condition = new HashMap<>();
        if (name !=null && !name.trim().isEmpty()){
            condition.put("name", name);
        }
        if (institution != null && !institution.trim().isEmpty()){
            condition.put("institution", institution);
        }
        if (startDate != null && !startDate.trim().isEmpty()){
            condition.put("startDate", startDate);
        }
        if (endDate != null && !endDate.trim().isEmpty()){
            condition.put("endDate", endDate);
        }
        if (state != null && !state.trim().isEmpty()){
            condition.put("state", state);
        }

        System.out.println("conditons:"+condition);
        ArrayList<PolicyListItem> ret = policyService.selectByCondition(condition);
        if (ret == null){
            System.out.println("select policy return value is null");
        }
        return ret;
    }

    @RequestMapping(value="/api/policy/content/{name}", method = RequestMethod.GET)
    public void getPolicyContent(@PathVariable String name, HttpServletResponse response){
        if (policyService.getPolicyContent(name, response) == false){
            System.out.println("GetPolicyContent error");
        }
    }

    @RequestMapping(value = "/api/policy/upload", method = RequestMethod.POST)
    public String FileUpload(@RequestParam(value = "institution") String institution,
                             @RequestParam(value = "issueDate") String issueDateStr,
                             MultipartFile file){

        System.out.println("upload file with institution=" + institution + "  issueDate=" + issueDateStr);

        //检查用户是否登录
        if (sessionService.getCurrentUserId() == 0){
            return "no login";
        }

        //检查用户权限
        if (!permissionService.poliPermissionExist(sessionService.getCurrentUserId())){
            return "no permission";
        }


        //存储文件
        if (!policyService.FileUpload(file)){
            return "fail";
        }

        //转换并检查发布日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date issueDate = null;
        try {
            issueDate = sdf.parse(issueDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "fail";
        }

        //存入数据库
        Policy policy = new Policy();
        policy.setName(file.getOriginalFilename());
        policy.setInstitution(institution);
        policy.setIssueDate(issueDate);
        policy.setState("有效");
        policy.setOperatorId(sessionService.getCurrentUserId());
        policy.setOperateTime(new Timestamp(System.currentTimeMillis()));

        if (policyService.insert(policy) == 0){
            return "fail";
        }
        return "ok";
    }

    @PostMapping(value = "/api/policy/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setRegulationState(@RequestBody PolicyListItem policyListItem){
        System.out.println("setRegulationState:" + policyListItem.getName());
        Policy policy = new Policy( policyListItem.getName(),
                                    policyListItem.getInstitution(),
                                    policyListItem.getIssueDate(),
                                    policyListItem.getState(),
                                    sessionService.getCurrentUserId(),
                                    new Timestamp(System.currentTimeMillis()));
        policyService.updateState(policy);

    }

}
