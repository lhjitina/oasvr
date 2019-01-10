package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.Contract;
import com.ks4pl.oasvr.mapper.ContractListItemMapper;
import com.ks4pl.oasvr.mapper.ContractMapper;
import com.ks4pl.oasvr.model.ContractListItem;
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
import java.util.HashMap;
import java.util.Map;

@Service
public class ContractService extends ServiceBase{
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ContractListItemMapper contractListItemMapper;
    @Autowired
    private ContractMapper contractMapper;

    public ArrayList<ContractListItem> selectByCondition(Map<String, Object> condition) {
        return contractListItemMapper.selectByCondition(condition);
    }

    public Integer total(Map<String, Object> con){
        return contractListItemMapper.total(con);
    }
    public Boolean getContractContent(String name, HttpServletResponse response) throws ServiceException{
        System.out.println("getContractContent filename=" + name);
        return FileUtil.getBinaryFileContent("contract", name, response);
    }

    public void FileUpload(Date issueDate, MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException{
        if (!FileUtil.FileUpload("contract", file)){
            logger.error("save file error");
            throw new ServiceException("save file error");
        }
        //存入数据库
        Contract contract = new Contract();
        contract.setName(file.getOriginalFilename());
        contract.setIssueDate(issueDate);
        contract.setState("有效");
        contract.setOperatorId(getCurrentUserId());
        contract.setOperateTime(new Timestamp(System.currentTimeMillis()));

        if (insert(contract) == 0){
            logger.error("insert contract into database fail");
            throw new ServiceException("insert contract into database fail");
        }
        logger.info("upload contract ok");
    }

    public Integer insert(Contract contract) throws SQLIntegrityConstraintViolationException{
        return contractMapper.insertone(contract);
    }

    public Integer updateState(String name, String state) throws SQLIntegrityConstraintViolationException{
        Contract contract = new Contract(0, name, null, state, getCurrentUserId(),
                                        new Timestamp(System.currentTimeMillis()));
        return contractMapper.updateStateByName(contract);
    }
}
