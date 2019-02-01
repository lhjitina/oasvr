package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.ShareInfo;
import com.ks4pl.oasvr.mapper.ShareInfoListItemMapper;
import com.ks4pl.oasvr.mapper.ShareInfoMapper;
import com.ks4pl.oasvr.model.ShareInfoListItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

@Service
public class InfoPortService extends ServiceBase {
    private static Logger logger = LogManager.getLogger();
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

    public void upload(String tag, MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException {
        if (!FileUtil.FileUpload("share", file)){
            throw new ServiceException("save file fail," + file.getOriginalFilename());
        }
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setTag(tag);
        shareInfo.setName(file.getOriginalFilename());
        shareInfo.setOperatorId(getCurrentUserId());
        shareInfo.setOperateTime(new Timestamp(System.currentTimeMillis()));
        if (shareInfoMapper.insert(shareInfo) == 0){
            throw new ServiceException("insert file to database fail" + shareInfo.toString());
        }
    }

    public void delete(String name) throws ServiceException{

        logger.info("delete: name="+name);
        FileUtil.delete("share", name);
        shareInfoMapper.deleteByName(name);
    }

    public void refresh(String tag, MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException {
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setName(file.getOriginalFilename());
        shareInfo.setTag(tag);
        shareInfo.setOperatorId(getCurrentUserId());
        shareInfo.setOperateTime(new Timestamp(System.currentTimeMillis()));
        logger.info("update:" + shareInfo.toString());
        if (shareInfoMapper.updateByName(shareInfo) == 0){
            throw new ServiceException("update database fail" + shareInfo.toString());
        }
        FileUtil.delete("share", file.getOriginalFilename());
        if (!FileUtil.FileUpload("share", file)){
            throw new ServiceException("save file fail," + file.getOriginalFilename());
        }
    }

    public ArrayList<ShareInfoListItem> fuzzyQuery(Map<String, Object> condition){
        String[] keys = condition.get("keys").toString().split(" |,|，");
        logger.info("info port fuzzy query keys count={} ", keys.length);
        for (int i = 0; i < keys.length; i++){
            logger.info("key {} is: {}", i, keys[i] );
        }
        return null;
    }
}
