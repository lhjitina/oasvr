package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.ShareInfo;
import com.ks4pl.oasvr.mapper.ShareInfoListItemMapper;
import com.ks4pl.oasvr.mapper.ShareInfoMapper;
import com.ks4pl.oasvr.model.ShareInfoListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public class InfoPortService extends ServiceBase {
    @Autowired
    private ShareInfoMapper shareInfoMapper;
    @Autowired
    private ShareInfoListItemMapper shareInfoListItemMapper;

    public ArrayList<ShareInfoListItem> selectByCondition(Map<String, Object> condition) {
        return shareInfoListItemMapper.selectByCondition(condition);
    }
    public Integer total(Map<String, Object> condition){
        return shareInfoListItemMapper.total(condition);
    }
    public Boolean getShareInfoContent(String name, HttpServletResponse response) throws ServiceException{
        return FileUtil.getBinaryFileContent("share", name, response);
    }

    public void upload(MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException {
        if (!FileUtil.FileUpload("share", file)){
            throw new ServiceException("save file fail," + file.getOriginalFilename());
        }
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setName(file.getOriginalFilename());
        shareInfo.setOperatorId(getCurrentUserId());
        shareInfo.setOperateTime(new Timestamp(System.currentTimeMillis()));
        if (shareInfoMapper.insert(shareInfo) == 0){
            throw new ServiceException("insert file to database fail" + shareInfo.toString());
        }
    }

    public void delete(String name){
        FileUtil.delete("share", name);
        shareInfoMapper.deleteByName(name);
    }

    public void refresh(MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException {
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setName(file.getOriginalFilename());
        shareInfo.setOperatorId(getCurrentUserId());
        shareInfo.setOperateTime(new Timestamp(System.currentTimeMillis()));
        if (shareInfoMapper.updateByName(shareInfo) == 0){
            throw new ServiceException("update database fail" + shareInfo.toString());
        }
        delete(file.getOriginalFilename());
        if (!FileUtil.FileUpload("share", file)){
            throw new ServiceException("save file fail," + file.getOriginalFilename());
        }
    }
}
