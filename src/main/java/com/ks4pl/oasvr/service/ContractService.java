package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.Contract;
import com.ks4pl.oasvr.entity.ContractTemplate;
import com.ks4pl.oasvr.mapper.ContractMapper;
import com.ks4pl.oasvr.mapper.ContractTemplateListItemMapper;
import com.ks4pl.oasvr.mapper.ContractTemplateMapper;
import com.ks4pl.oasvr.model.ContractTemplateListItem;
import com.ks4pl.oasvr.model.ContractUpdate;
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
public class ContractService extends ServiceBase{
    @Autowired
    ContractMapper contractMapper;

    private static final Logger logger = LogManager.getLogger();

    public ArrayList<Contract> selectByCondition(Map<String, Object> condition) {
        return contractMapper.selectByCondition(condition);
    }

    public Integer total(Map<String, Object> con){
        return contractMapper.total(con);
    }
    public Boolean getContractContent(String name, HttpServletResponse response) throws ServiceException{
        logger.info("getContractContent filename=" + name);
        return FileUtil.getBinaryFileContent("contract", name, response);
    }

    public void FileUpload(String partner, String type, Date start, Date end,
                           Integer autoRenewal, String digest, MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException {
        if (!FileUtil.FileUpload("contract", file)){
            logger.error("save file error");
            throw new ServiceException("save file error");
        }
        //存入数据库
        Contract contract = new Contract();
        contract.setName(file.getOriginalFilename());
        contract.setPartner(partner);
        contract.setType(type);
        contract.setStart(start);
        contract.setEnd(end);
        contract.setAutoRenewal(autoRenewal);
        contract.setDigest(digest);
        contract.setOperatorId(getCurrentUserId());
        contract.setOperateTime(new Timestamp(System.currentTimeMillis()));

        if (contractMapper.insertCon(contract) == 0){
            logger.error("insert contract into database fail");
            throw new ServiceException("insert contract into database fail");
        }
        logger.info("upload contract ok");
    }

    public Integer deleteCon(String name){
        return contractMapper.deleteCon(name);
    }

    public Integer updateCon(ContractUpdate contractUpdate)
            throws SQLIntegrityConstraintViolationException{
        return contractMapper.updateCon(contractUpdate);
    }
}
