package com.ks4pl.oasvr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Controller
public class RegulationController {
    private ArrayList<Regulation> Regulations;


    public RegulationController() {
        Regulations = new ArrayList<>();
        Date d =new Date();
        System.out.println(d.toString());
        Calendar od = Calendar.getInstance();

        Regulation r = new Regulation(1, "20180521凯晟周例会会议纪要", "方案策划部", d, 1, od);
        Regulations.add(r);
        r = new Regulation(1, "开行方案2", "市场营销部", d, 1, od);
        Regulations.add(r);
        r = new Regulation(1, "开行方案3", "运营管理部", d, 1, od);
        Regulations.add(r);

    }

    @RequestMapping(value = "/api/regulation/list", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Regulation> GetRegulations(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "department", required = false) String department,
                                                @RequestParam(value = "startDate", required = false) String startDate,
                                                @RequestParam(value = "endDate", required = false) String endDate){
        if (department != null)
            System.out.println("...department=" + department);
        else
            System.out.println("....department is null....");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sd = null;
        Date ed = null;
        if (startDate != null){
            try {
                sd = sdf.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (endDate!= null){
            try {
                ed = sdf.parse(endDate);
            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        ArrayList<Regulation> reg = new ArrayList<>();
        for(int i = 0; i < Regulations.size(); i++){
            if ((name != null) && !name.isEmpty() && !name.contains(Regulations.get(i).getName()) ){
                continue;
            }
            if ((department != null) && !department.isEmpty() && !department.equals(Regulations.get(i).getDepartment())){
                continue;
            }
            if ((sd != null) && sd.after(Regulations.get(i).getPubDate())){
                continue;
            }
            if ((ed != null) && ed.before(Regulations.get(i).getPubDate())){
                continue;
            }
            System.out.println("add a reg");
            reg.add(Regulations.get(i));
        }

        return reg;
    }

    @RequestMapping(value="/api/regulation/content/{name}", method = RequestMethod.GET)
    public void GetRegulationContent(@PathVariable String name, HttpServletResponse response) throws IOException {
        System.out.println("........get content............");
        File regFile = new File("e:/projects/" + name + ".pdf");
        FileInputStream fis = new FileInputStream(regFile);

        Long flen = regFile.length();
        byte data[] = new byte[flen.intValue()];

        fis.read(data, 0, flen.intValue());

        ServletOutputStream sos = response.getOutputStream();
        sos.write(data, 0, flen.intValue());

        response.setContentType("application/octet-stream");

        fis.close();
        sos.close();
    }

 }
