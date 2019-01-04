package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.entity.Contract;
import com.ks4pl.oasvr.entity.Policy;
import com.ks4pl.oasvr.model.ContractListItem;
import com.ks4pl.oasvr.model.PolicyListItem;
import com.ks4pl.oasvr.service.ContractService;
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
public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/api/front/contract/list", method = RequestMethod.GET)
    public ArrayList<ContractListItem> frontGetContractList(String name,
                                                          String startDate,
                                                          String endDate,
                                                          String state) {
        System.out.println("enter:   /api/front/contract/list");
        return getContractList(name, startDate, endDate, state);
    }

    @RequestMapping(value = "/api/console/contract/list", method = RequestMethod.GET)
    public ArrayList<ContractListItem> consoleGetContractList(String name,
                                                          String startDate,
                                                          String endDate,
                                                          String state){
        System.out.println("enter:/api/console/contract/list");

        return getContractList(name, startDate, endDate, state);
    }

    private ArrayList<ContractListItem> getContractList(String name, String startDate, String endDate, String state){
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
        if (state != null && !state.trim().isEmpty()){
            condition.put("state", state);
        }

        System.out.println("conditons:"+condition);
        ArrayList<ContractListItem> ret = contractService.selectByCondition(condition);
        if (ret == null){
            System.out.println("select contract return value is null");
        }
        return ret;
    }

    @RequestMapping(value="/api/contract/content/{name}", method = RequestMethod.GET)
    public void getContractContent(@PathVariable String name, HttpServletResponse response){
        if (contractService.getContractContent(name, response) == false){
            System.out.println("getContractContent error");
        }
    }

    @RequestMapping(value = "/api/contract/upload", method = RequestMethod.POST)
    public String FileUpload(@RequestParam(value = "issueDate") String issueDateStr,
                             MultipartFile file){

        System.out.println("upload file with issueDate=" + issueDateStr);

        //检查用户是否登录
        if (sessionService.getCurrentUserId() == 0){
            return "no login";
        }

        //检查用户权限
        if (!permissionService.conPermissionExist(sessionService.getCurrentUserId())){
            return "no permission";
        }

        //存储文件
        if (!contractService.FileUpload(file)){
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
        Contract contract = new Contract();
        contract.setName(file.getOriginalFilename());
        contract.setIssueDate(issueDate);
        contract.setState("有效");
        contract.setOperatorId(sessionService.getCurrentUserId());
        contract.setOperateTime(new Timestamp(System.currentTimeMillis()));

        if (contractService.insert(contract) == 0){
            System.out.println("insert contract fail");
            return "fail";
        }
        System.out.println("insert contract ok");
        return "ok";
    }

    @PostMapping(value = "/api/contract/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setContractState(@RequestBody ContractListItem contractListItem){
        System.out.println("setContractState:" + contractListItem.getName());
        Contract contract = new Contract(0,
                contractListItem.getName(),
                contractListItem.getIssueDate(),
                contractListItem.getState(),
                sessionService.getCurrentUserId(),
                new Timestamp(System.currentTimeMillis()));
        contractService.updateState(contract);

    }
}
