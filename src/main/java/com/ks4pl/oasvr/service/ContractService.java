package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.Contract;
import com.ks4pl.oasvr.mapper.ContractListItemMapper;
import com.ks4pl.oasvr.mapper.ContractMapper;
import com.ks4pl.oasvr.model.ContractListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ContractService {
    @Autowired
    private ContractListItemMapper contractListItemMapper;
    @Autowired
    private ContractMapper contractMapper;

    public ArrayList<ContractListItem> selectByCondition(Map<String, Object> condition) {
        return contractListItemMapper.selectByCondition(condition);
    }

    public Boolean getContractContent(String name, HttpServletResponse response) {
        System.out.println("getContractContent filename=" + name);
        return FileUtil.getBinaryFileContent("contract", name, response);
    }

    public Boolean FileUpload(MultipartFile file) {
        return FileUtil.FileUpload("contract", file);
    }

    public Integer insert(Contract contract){
        return contractMapper.insertone(contract);
    }

    public Integer updateState(Contract contract){
        return contractMapper.updateStateByName(contract);
    }
}
