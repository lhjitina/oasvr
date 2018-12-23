package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.Policy;
import com.ks4pl.oasvr.mapper.PolicyListItemMapper;
import com.ks4pl.oasvr.mapper.PolicyMapper;
import com.ks4pl.oasvr.model.PolicyListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import com.ks4pl.oasvr.service.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Service
public class PolicyService {

    @Autowired
    private PolicyListItemMapper policyListItemMapper;
    @Autowired
    private PolicyMapper policyMapper;

    public ArrayList<PolicyListItem> selectByCondition(Map<String, Object> condition) {
        return policyListItemMapper.selectByCondition(condition);
    }

    public Boolean getPolicyContent(String name, HttpServletResponse response) {
        System.out.println("getPolicyContent filename=" + name);
        return FileUtil.getBinaryFileContent("policy", name, response);
    }

    public Boolean FileUpload(MultipartFile file) {
        return FileUtil.FileUpload("policy", file);
    }

    public Integer insert(Policy policy){
        return policyMapper.insert(policy);
    }

    public Integer updateState(Policy policy){
        return policyMapper.updateStateByName(policy);
    }

}
