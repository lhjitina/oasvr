package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.Department;
import com.ks4pl.oasvr.mapper.RegulationListItemMapper;
import com.ks4pl.oasvr.mapper.RegulationMapper;
import com.ks4pl.oasvr.entity.Regulation;
import com.ks4pl.oasvr.model.RegulationListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;


@Service
public class RegulationService {

    @Autowired
    private RegulationMapper regulationMapper;

    @Autowired
    private RegulationListItemMapper regulationListItemMapper;


    public Boolean getRegulationContent(String name, HttpServletResponse response){
        return FileUtil.getBinaryFileContent("regulation", name, response);
    }

    public Boolean FileUpload(MultipartFile file) {
        return FileUtil.FileUpload("regulation", file);
    }

    public Integer insert(Regulation regulation){
        return regulationMapper.insert(regulation);
    }

    public ArrayList<RegulationListItem> selectListItemByCondition(Map<String, Object> condition){
        return regulationListItemMapper.selectByCondition(condition);
    }

    public Integer updateState(Regulation regulation){
        return regulationMapper.updateStateByName(regulation);
    }

}
