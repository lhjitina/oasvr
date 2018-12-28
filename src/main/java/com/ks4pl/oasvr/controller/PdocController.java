package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.entity.PartnerDoc;
import com.ks4pl.oasvr.model.PdocListItem;
import com.ks4pl.oasvr.service.PdocService;
import com.ks4pl.oasvr.service.PermissionService;
import com.ks4pl.oasvr.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    public ArrayList<PdocListItem> frontGetPdocList(String name, String partner){
        System.out.println("enter:   /api/front/pdoc/list");
        return getPdocList(name, partner);
    }

    @RequestMapping(value = "/api/console/pdoc/list", method = RequestMethod.GET)
    public ArrayList<PdocListItem> consoleGetPdocList(String name, String partner){
        System.out.println("enter:/api/console/pdoc/list  partner=" + partner);

        return getPdocList(name, partner);
    }

    private ArrayList<PdocListItem> getPdocList(String name, String partner){
        Map<String, Object> condition = new HashMap<>();
        if (name !=null && !name.trim().isEmpty()){
            condition.put("name", name);
        }
        if (partner != null && !partner.trim().isEmpty()){
            condition.put("institution", partner);
        }
        System.out.println("conditons:"+condition);
        ArrayList<PdocListItem> ret = pdocService.selectByCondition(condition);
        if (ret == null){
            System.out.println("select pdoc return value is null");
        }
        for (int i = 0; i < ret.size(); i++){
            System.out.println(ret.get(i).toString());
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
