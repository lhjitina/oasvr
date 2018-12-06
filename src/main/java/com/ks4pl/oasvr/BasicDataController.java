package com.ks4pl.oasvr;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@Controller
public class BasicDataController {

    private ArrayList<Department> Departments = new ArrayList<>();

    public BasicDataController() {
    }

/*    @RequestMapping(value = "/api/departments", method= RequestMethod.GET)
    @ResponseBody
    public ArrayList<Department> getDepartments(){
        Departments.clear();
        Departments.add(new Department(10, "方案策划部"));
        Departments.add(new Department(20, "市场营销部"));
        Departments.add(new Department(30, "运营管理部"));
        Departments.add(new Department(40, "综合管理部"));
        Departments.add(new Department(50, "信息技术部"));
        return Departments;
    }
*/
    @RequestMapping(value = "/api/department/list", method= RequestMethod.GET)
    @ResponseBody
    public ArrayList<String>  getDepartments(HttpServletResponse response) throws IOException {

        ArrayList<String> deps = new ArrayList<>();
        deps.add("市场营销部");
        deps.add("运营管理部");
        deps.add("方案策划部");
        return deps;
//        JSONArray deps=new JSONArray();
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("Id", 30);
//        jsonObject.put("Name", "市场营销部");
//        deps.put(jsonObject);
//
//        jsonObject = new JSONObject();
//        jsonObject.put("Id", 30);
//        jsonObject.put("Name", "运营管理部");
//        deps.put(jsonObject);
//
//        jsonObject = new JSONObject();
//        jsonObject.put("Id", 30);
//        jsonObject.put("Name", "方案策划部");
//        deps.put(jsonObject);
//
//        response.setContentType("application/json;charset=utf-8");
//        PrintWriter pw = response.getWriter();
//
//        pw.write(deps.toString());

    }
}
