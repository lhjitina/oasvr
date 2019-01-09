package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.Policy;
import com.ks4pl.oasvr.entity.Summary;
import com.ks4pl.oasvr.mapper.SummaryListItemMapper;
import com.ks4pl.oasvr.mapper.SummaryMapper;
import com.ks4pl.oasvr.model.PolicyListItem;
import com.ks4pl.oasvr.model.SummaryListItem;
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
public class SummaryService extends ServiceBase{
    private static Logger logger = LogManager.getLogger();
    @Autowired
    private SummaryMapper summaryMapper;
    @Autowired
    SummaryListItemMapper summaryListItemMapper;

    public ArrayList<SummaryListItem> selectByCondition(Map<String, Object> condition) {
        return summaryListItemMapper.selectByCondition(condition);
    }
    public Integer total(Map<String, Object> con){
        return summaryListItemMapper.total(con);
    }
    public Boolean getSummaryContent(String name, HttpServletResponse response) {
        System.out.println("getSummaryContent filename=" + name);
        return FileUtil.getBinaryFileContent("summary", name, response);
    }

    public void FileUpload(Date meetingDate, MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException{
        if (!FileUtil.FileUpload("summary", file)) {
            throw new ServiceException("save summary fail:" + meetingDate + file.getOriginalFilename());
        }
        //存入数据库
        Summary summary = new Summary();
        summary.setName(file.getOriginalFilename());
        summary.setMeetingDate(meetingDate);
        summary.setOperatorId(getCurrentUserId());
        summary.setOperateTime(new Timestamp(System.currentTimeMillis()));
        if (summaryMapper.insert(summary) == 0){
            logger.error("save to database fail");
            throw new ServiceException("save summary into db fail: " + meetingDate + file.getOriginalFilename());
        }
    }

    public void deleteByName(String name) throws ServiceException{
        if (summaryMapper.deleteByName(name) == 0){
            throw new ServiceException("delete summary fail: " + name);
        }
    }
 }
