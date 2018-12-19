package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.entity.Regulation;
import com.ks4pl.oasvr.model.RegulationListItem;
import com.ks4pl.oasvr.service.DepartmentService;
import com.ks4pl.oasvr.service.RegulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class RegulationController {

    @Autowired
    private RegulationService regulationService;

    @Autowired
    private DepartmentService departmentService;

    public RegulationController() {

    }

    @RequestMapping(value = "/api/regulation/list", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<RegulationListItem> GetRegulations(@RequestParam(value = "name", required = false) String name,
                                                        @RequestParam(value = "department", required = false) String department,
                                                        @RequestParam(value = "startDate", required = false) String startDate,
                                                        @RequestParam(value = "endDate", required = false) String endDate){

        return regulationService.getAllRegulationListItem();
    }

    @RequestMapping(value="/api/regulation/content/{name}", method = RequestMethod.GET)
    public void GetRegulationContent(@PathVariable String name, HttpServletResponse response) throws IOException {

        System.out.println(".....get reuglation content........name:"+name);
        Boolean bGet = regulationService.getRegulationContent(name, response);


        /*       System.out.println("........get content............");
        File regFile = new File(getPath() + name);
        System.out.println("........file............" + getPath() + name);
        FileInputStream fis = new FileInputStream(regFile);

        Long flen = regFile.length();
        byte data[] = new byte[flen.intValue()];

        fis.read(data, 0, flen.intValue());

        ServletOutputStream sos = response.getOutputStream();
        sos.write(data, 0, flen.intValue());

        response.setContentType("application/octet-stream");

        fis.close();
        sos.close();
        */
    }


    @RequestMapping(value = "/api/regulation/upload", method = RequestMethod.POST)
    @ResponseBody
    public String FileUpload(@RequestParam(value = "department") Integer departmentId,
                             @RequestParam(value = "issueDate") String issueDateStr,
                             MultipartFile file){

        System.out.println("upload file with department id=" + departmentId + "  issueDate=" + issueDateStr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date issueDate = null;
        try {
            issueDate = sdf.parse(issueDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "fail";
        }

        if (!regulationService.FileUpload(file)){
             return "fail";
        }
        else if (!departmentService.isIdValid(departmentId)){
            return "fail";
        }
        else{
            Regulation regulation = new Regulation();
            regulation.setId(0);
            regulation.setName(file.getOriginalFilename());
            regulation.setDepartment(departmentId);
            regulation.setIssueDate(issueDate);
            regulation.setState("有效");
            regulation.setOperatorId(1);
            regulation.setOperateTime(new Timestamp(System.currentTimeMillis()));

            if (regulationService.insert(regulation) == 0){
                return "fail";
            }
        }
        return "ok";
    }
}
