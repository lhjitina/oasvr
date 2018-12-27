package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.entity.PartnerDoc;
import com.ks4pl.oasvr.entity.Policy;
import com.ks4pl.oasvr.model.PolicyListItem;
import com.ks4pl.oasvr.service.PdocService;
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
public class PdocController {

    @Autowired
    private PdocService pdocService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/api/front/pdoc/list", method = RequestMethod.GET)
    public ArrayList<PdocListItem> frontGetPolicyList(String name,
                                                        String institution,
                                                        String startDate,
                                                        String endDate,
                                                        String state) {
        System.out.println("enter:   /api/front/policy/list");
        return getPolicyList(name, institution, startDate, endDate, state);
    }

    @RequestMapping(value = "/api/console/pdoc/list", method = RequestMethod.GET)
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

    @RequestMapping(value="/api/pdoc/content", method = RequestMethod.GET)
    public void getPolicyContent(@RequestParam String name,
                                 @RequestParam String partner,
                                 HttpServletResponse response){
        if (pdocService.getPdocContent(name, response) == false){
            System.out.println("GetPolicyContent error");
        }
    }

    @RequestMapping(value = "/api/pdoc/upload", method = RequestMethod.POST)
    public String FileUpload(@RequestParam String partner,  MultipartFile file){

        System.out.println("upload file with partner=" + partner);

        //检查用户是否登录
        if (sessionService.getCurrentUserId() == 0){
            return "no login";
        }

        //检查用户权限
        if (!permissionService.docPermissionExist(sessionService.getCurrentUserId())){
            return "no permission";
        }


        //存储文件
        if (!pdocService.FileUpload(file)){
            return "fail";
        }

        //存入数据库
        PartnerDoc partnerDoc = new PartnerDoc();
        partnerDoc.setName(file.getOriginalFilename());
        partnerDoc.setPartner(partner);
        partnerDoc.setOperatorId(sessionService.getCurrentUserId());
        partnerDoc.setOperateTime(new Timestamp(System.currentTimeMillis()));

        if (pdocService.insert(partnerDoc) == 0){
            return "fail";
        }
        return "ok";
    }

}
