package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.Department;
import com.ks4pl.oasvr.mapper.RegulationListItemMapper;
import com.ks4pl.oasvr.mapper.RegulationMapper;
import com.ks4pl.oasvr.entity.Regulation;
import com.ks4pl.oasvr.model.RegulationListItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class RegulationService extends ServiceBase{
    private static Logger logger = LogManager.getLogger();
    @Autowired
    private RegulationMapper regulationMapper;

    @Autowired
    private RegulationListItemMapper regulationListItemMapper;

    public Boolean getRegulationContent(String name, HttpServletResponse response) throws ServiceException{
        return FileUtil.getBinaryFileContent("regulation", name, response);
    }

    public void FileUpload(Integer departmentId, Date issueDate, MultipartFile file)
            throws ServiceException {
        if (!FileUtil.FileUpload("regulation", file)) {
            logger.error("save file fail: {}...{}...{}", departmentId, issueDate, file.getOriginalFilename());
            throw new ServiceException("save file fail," + departmentId + issueDate + file.getOriginalFilename());
        }
        //存入数据库
        Regulation regulation = new Regulation();
        regulation.setName(file.getOriginalFilename());
        regulation.setDepartment(departmentId);
        regulation.setIssueDate(issueDate);
        regulation.setState("有效");
        regulation.setOperatorId(getCurrentUserId());
        regulation.setOperateTime(new Timestamp(System.currentTimeMillis()));

        if (regulationMapper.insert(regulation) == 0){
            logger.error("insert regulation into db fail: {}...{}...{}", departmentId, issueDate, file.getOriginalFilename());
            throw new ServiceException("insert regulation into db fail");
        }
    }

    public ArrayList<RegulationListItem> selectListItemByCondition(Map<String, Object> condition){
        return regulationListItemMapper.selectByCondition(condition);
    }
    public Integer total(Map<String, Object> con){
        return regulationListItemMapper.total(con);
    }

    public void updateState(String name, Integer departmentId, String state) throws ServiceException{
        Regulation regulation = new Regulation(name, departmentId, null, state,
                                        getCurrentUserId(),
                                        new Timestamp(System.currentTimeMillis()));
        if (regulationMapper.updateStateByName(regulation) == 0){
            throw new ServiceException("update state to db fail");
        }
    }

}
