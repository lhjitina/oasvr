package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.PartnerDoc;
import com.ks4pl.oasvr.entity.Policy;
import com.ks4pl.oasvr.mapper.PdocMapper;
import com.ks4pl.oasvr.mapper.PolicyListItemMapper;
import com.ks4pl.oasvr.mapper.PolicyMapper;
import com.ks4pl.oasvr.model.PolicyListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

@Service
public class PdocService {
    @Autowired
    private PdocMapper pdocMapper;


    public ArrayList<PolicyListItem> selectByCondition(Map<String, Object> condition) {
        return policyListItemMapper.selectByCondition(condition);
    }

    public Boolean getPdocContent(String name, HttpServletResponse response) {
        System.out.println("getPdocContent filename=" + name);
        return FileUtil.getBinaryFileContent("pdoc", name, response);
    }

    public Boolean FileUpload(MultipartFile file) {
        return FileUtil.FileUpload("pdoc", file);
    }

    public Integer insert(PartnerDoc partnerDoc){
        return pdocMapper.insert(partnerDoc);
    }

    public Integer delete(String partner, String name){
        return pdocMapper.deleteByPartnerAndName(partner, name);;
    }

}
