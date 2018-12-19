package com.ks4pl.oasvr.service;

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



    public static String getPath(){
        String os_name = System.getProperties().get("os.name").toString().toLowerCase();
        System.out.println("os name is ...."+os_name);
        if(os_name.contains("windows")) {
            return "e:/projects/data/";
        }
        else{
            return "/Users/lhj/work/";
        }
    }

    public Boolean getRegulationContent(String name, HttpServletResponse response){
        File regFile = new File(getPath() + name);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(regFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        Long flen = regFile.length();
        byte data[] = new byte[flen.intValue()];

        try {
            fis.read(data, 0, flen.intValue());
            ServletOutputStream sos = response.getOutputStream();
            sos.write(data, 0, flen.intValue());

            response.setContentType("application/octet-stream");

            fis.close();
            sos.close();
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean FileUpload(MultipartFile file) {
        System.out.println("uploadint  file......");
        if (file == null){
            System.out.println("file upload: error, file is null");
        }
        String originalFileName = file.getOriginalFilename();
        System.out.println("uploadint  file......name:"+originalFileName);
        try {
            byte[] bytes = file.getBytes();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(getPath() + originalFileName));
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("upload ok");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Integer insert(Regulation regulation){
        return regulationMapper.insert(regulation);
    }
}
