package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.ContractTemplate;
import com.ks4pl.oasvr.mapper.ContractTemplateListItemMapper;
import com.ks4pl.oasvr.mapper.ContractTemplateMapper;
import com.ks4pl.oasvr.model.ContractTemplateListItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
public class ContractTemplateService extends ServiceBase{
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ContractTemplateListItemMapper contractTemplateListItemMapper;
    @Autowired
    private ContractTemplateMapper contractTemplateMapper;

    public ArrayList<ContractTemplateListItem> selectByCondition(Map<String, Object> condition) {
        return contractTemplateListItemMapper.selectByCondition(condition);
    }

    public Integer total(Map<String, Object> con){
        return contractTemplateListItemMapper.total(con);
    }
    public Boolean getContractTemplateContent(String name, HttpServletResponse response) throws ServiceException{
        System.out.println("getContractTemplateContent filename=" + name);
        return FileUtil.getBinaryFileContent("contractTemplate", name, response);
    }

    public void FileUpload(Date issueDate, MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException{
        if (!FileUtil.FileUpload("contractTemplate", file)){
            logger.error("save file error");
            throw new ServiceException("save file error");
        }
        //存入数据库
        ContractTemplate contractTemplate = new ContractTemplate();
        contractTemplate.setName(file.getOriginalFilename());
        contractTemplate.setIssueDate(issueDate);
        contractTemplate.setState("有效");
        contractTemplate.setOperatorId(getCurrentUserId());
        contractTemplate.setOperateTime(new Timestamp(System.currentTimeMillis()));

        if (contractTemplateMapper.insertone(contractTemplate) == 0){
            logger.error("insert contractTemplate into database fail");
            throw new ServiceException("insert contractTemplate into database fail");
        }
        logger.info("upload contractTemplate ok");
    }

    public Integer updateState(String name, String state)
            throws SQLIntegrityConstraintViolationException, ServiceException{
        ContractTemplate contractTemplate = new ContractTemplate(0, name, null, state, getCurrentUserId(),
                                        new Timestamp(System.currentTimeMillis()));
        return contractTemplateMapper.updateStateByName(contractTemplate);
    }
}
