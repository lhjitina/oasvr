package com.ks4pl.oasvr.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


public class FileUtil {

    public static Boolean getBinaryFileContent(String type, String name, HttpServletResponse response){
        System.out.println("....get file: " + getPath(type) + name);
        File file = new File(getPath(type) + name);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        Long flen = file.length();
        byte data[] = new byte[flen.intValue()];

        try {
            fis.read(data, 0, flen.intValue());
            ServletOutputStream sos = response.getOutputStream();
            sos.write(data, 0, flen.intValue());
            response.setContentType("application/octet-stream");
            fis.close();
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getPath(String type){
        String path = null;
        String os_name = System.getProperties().get("os.name").toString().toLowerCase();
        System.out.println("os name is ...."+os_name);
        if(os_name.contains("windows")) {
            path = "e:/projects/data/";
        }
        else{
            path = "/Users/lhj/work/data/";
        }
        switch(type) {
            case "policy": {
                path += "policy/";
                break;
            }
            case "regulation": {
                path += "regulation/";
                break;
            }
            case "summary": {
                path += "summary/";
                break;
            }
            default:{
                path += "other/";
                break;
            }
        }
        return path;
    }

    public static Boolean FileUpload(String type, MultipartFile file) {
        System.out.println("uploadint  file......");
        if (file == null){
            System.out.println("file upload: error, file is null");
            return false;
        }
        String originalFileName = file.getOriginalFilename();
        System.out.println("uploadint  file......name:"+originalFileName);
        try {
            byte[] bytes = file.getBytes();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(getPath(type) + originalFileName));
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
}
