package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.entity.Policy;
import com.ks4pl.oasvr.mapper.PolicyListItemMapper;
import com.ks4pl.oasvr.mapper.PolicyMapper;
import com.ks4pl.oasvr.model.PolicyListItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.ks4pl.oasvr.service.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Service
public class PolicyService extends ServiceBase{
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private PolicyListItemMapper policyListItemMapper;
    @Autowired
    private PolicyMapper policyMapper;

    public ArrayList<PolicyListItem> selectByCondition(Map<String, Object> condition) {
        return policyListItemMapper.selectByCondition(condition);
    }
    public Integer total(HashMap<String, Object> con){
        return policyListItemMapper.total(con);
    }
    public Boolean getPolicyContent(String name, HttpServletResponse response) {
        System.out.println("getPolicyContent filename=" + name);
        return FileUtil.getBinaryFileContent("policy", name, response);
    }

    public void FileUpload(String institution, Date issueDate, MultipartFile file)
            throws ServiceException{
        if (!FileUtil.FileUpload("policy", file)) {
            logger.error("save file fail,{}...{}...{}", institution, issueDate,file.getOriginalFilename());
            throw new ServiceException("save file fail");
        }
        //存入数据库
        Policy policy = new Policy();
        policy.setName(file.getOriginalFilename());
        policy.setInstitution(institution);
        policy.setIssueDate(issueDate);
        policy.setState("有效");
        policy.setOperatorId(getCurrentUserId());
        policy.setOperateTime(new Timestamp(System.currentTimeMillis()));
        if (policyMapper.insert(policy) == 0) {
            logger.error("insert file into db fail,{}...{}...{}", institution, issueDate, file.getOriginalFilename());
            throw new ServiceException("insert file into db fail," + institution + issueDate + file.getOriginalFilename());
        }
    }

    public Integer updateState(String name, String institution, String state) {
        Policy policy = new Policy( name, institution, null, state, getCurrentUserId(), new Timestamp(System.currentTimeMillis()));
        return policyMapper.updateStateByName(policy);
    }

}
