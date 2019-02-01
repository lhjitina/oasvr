package com.ks4pl.oasvr;

import com.ks4pl.oasvr.service.FileUtil;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration
public class MyConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        File tmpFile = new File(FileUtil.getPath("tmp"));
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(FileUtil.getPath("tmp"));

        //单个文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(10));
       /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(100));
        return factory.createMultipartConfig();
    }

}
