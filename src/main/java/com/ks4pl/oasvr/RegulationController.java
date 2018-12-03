package com.ks4pl.oasvr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Controller
public class RegulationController {
    private ArrayList<Regulation> Regulations;


    public RegulationController() {
        Regulations = new ArrayList<Regulation>();
        Date d =new Date();
        System.out.println(d.toString());
        Calendar od = Calendar.getInstance();

        Regulation r = new Regulation(1, "20180521凯晟周例会会议纪要", "策划部", d, 1, od);
        Regulations.add(r);
        r = new Regulation(1, "开行方案2", "策划部", d, 1, od);
        Regulations.add(r);
        r = new Regulation(1, "开行方案3", "策划部", d, 1, od);
        Regulations.add(r);

    }

    @RequestMapping(value = "/api/regulations", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Regulation> GetRegulations(){
        System.out.println(".....aaaaaaaaaaaaaabbbbbbb...");
        return Regulations;
    }

    @RequestMapping(value="/api/regulation/{name}", method = RequestMethod.GET)

    public void GetRegulationContent(@PathVariable String name, HttpServletResponse response) throws IOException {
        System.out.println("........get content............");
        File regFile = new File("/Users/lhj/work/ksoa/oasvr/" + name + ".pdf");
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
