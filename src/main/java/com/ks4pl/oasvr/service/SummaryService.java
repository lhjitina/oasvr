package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.Policy;
import com.ks4pl.oasvr.entity.Summary;
import com.ks4pl.oasvr.mapper.SummaryListItemMapper;
import com.ks4pl.oasvr.mapper.SummaryMapper;
import com.ks4pl.oasvr.model.PolicyListItem;
import com.ks4pl.oasvr.model.SummaryListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

@Service
public class SummaryService {

    @Autowired
    private SummaryMapper summaryMapper;
    @Autowired
    SummaryListItemMapper summaryListItemMapper;

    public ArrayList<SummaryListItem> selectByCondition(Map<String, Object> condition) {
        return summaryListItemMapper.selectByCondition(condition);
    }

    public Boolean getSummaryContent(String name, HttpServletResponse response) {
        System.out.println("getSummaryContent filename=" + name);
        return FileUtil.getBinaryFileContent("summary", name, response);
    }

    public Boolean FileUpload(MultipartFile file) {
        return FileUtil.FileUpload("summary", file);
    }

    public Integer insert(Summary summary){
        return summaryMapper.insert(summary);
    }

    public Integer deleteByName(String name) { return summaryMapper.deleteByName(name);}
 }
