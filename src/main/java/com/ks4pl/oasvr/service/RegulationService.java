package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.mapper.RegulationListItemMapper;
import com.ks4pl.oasvr.mapper.RegulationMapper;
import com.ks4pl.oasvr.entity.Regulation;
import com.ks4pl.oasvr.model.RegulationListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Map;

@Service
public class RegulationService {

    @Autowired
    private RegulationMapper regulationMapper;

    @Autowired
    private RegulationListItemMapper regulationListItemMapper;

    public ArrayList<Regulation> getAllReg(){
        return regulationMapper.selectAll();
    }

    public ArrayList<RegulationListItem> getAllRegulationListItem(){
        return regulationListItemMapper.selectAll();
    }

    public byte[] getRegulationContent(String name){
        Map<String, Object> map = regulationListItemMapper.selectContent(name);
        return (byte[])map.get("content");

    }
}
