package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.PartnerDoc;
import com.ks4pl.oasvr.mapper.PdocListItemMapper;
import com.ks4pl.oasvr.mapper.PdocMapper;
import com.ks4pl.oasvr.model.PdocListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdocService extends ServiceBase{
    @Autowired
    private PdocMapper pdocMapper;
    @Autowired
    private PdocListItemMapper pdocListItemMapper;

    public ArrayList<PdocListItem> selectByCondition(Map<String, Object> condition) {
        return pdocListItemMapper.selectByCondition(condition);
    }
    public Integer total(Map<String, Object> con){
        return pdocListItemMapper.total(con);
    }
    public Boolean getPdocContent(String name, HttpServletResponse response) throws ServiceException{
        System.out.println("getPdocContent filename=" + name);
        return FileUtil.getBinaryFileContent("pdoc", name, response);
    }

    public void FileUpload(String partner, MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException {
        if (!FileUtil.FileUpload("pdoc", file)){
            throw new ServiceException("save file fail," + partner + file.getOriginalFilename());
        }
        PartnerDoc partnerDoc = new PartnerDoc();
        partnerDoc.setName(file.getOriginalFilename());
        partnerDoc.setPartner(partner);
        partnerDoc.setOperatorId(getCurrentUserId());
        partnerDoc.setOperateTime(new Timestamp(System.currentTimeMillis()));
        if (pdocMapper.insert(partnerDoc) == 0){
            throw new ServiceException("insert file to database fail" + partnerDoc.toString());
        }
    }

    public Integer delete(String name){
        FileUtil.delete("pdoc", name);
        return pdocMapper.deleteByName(name);
    }

}
