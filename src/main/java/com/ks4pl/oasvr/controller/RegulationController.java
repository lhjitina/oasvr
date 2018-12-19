package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.entity.Department;
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
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class RegulationController {
    private ArrayList<Regulation> Regulations;

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
        byte[] content = regulationService.getRegulationContent(name);

        File f = new File("testblob.pdf");

        FileOutputStream fos = new FileOutputStream(f);

        fos.write(content);
        fos.close();

            ServletOutputStream sos = response.getOutputStream();
            response.setContentType("application/octet-stream");
        sos.write(content);
            sos.close();

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
    @RequestMapping(value = "/api/regmgt/list", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Regulation> GetRegulationMgts(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "department", required = false) String department,
                                                @RequestParam(value = "startDate", required = false) String startDate,
                                                @RequestParam(value = "endDate", required = false) String endDate){
        return null;
    }

    @RequestMapping(value = "/api/regulation/upload", method = RequestMethod.POST)
    @ResponseBody
    public String FileUpload(@RequestParam(value = "department") Integer departmentId, MultipartFile file){

        System.out.println("upload file with department id=" + departmentId);
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
            regulation.setIssueDate(new Date());
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
