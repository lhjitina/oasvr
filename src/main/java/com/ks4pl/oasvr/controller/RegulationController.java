package com.ks4pl.oasvr.controller;


import com.ks4pl.oasvr.MyUtils;
import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.entity.Regulation;
import com.ks4pl.oasvr.model.RegulationListItem;
import com.ks4pl.oasvr.service.DepartmentService;
import com.ks4pl.oasvr.service.PermissionService;
import com.ks4pl.oasvr.service.RegulationService;
import com.ks4pl.oasvr.service.SessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RegulationController extends ControllerBase{
    private static Logger logger = LogManager.getLogger();
    @Autowired
    private RegulationService regulationService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/api/front/regulation/list", method = RequestMethod.POST)
    public RespPage frontGetRegulationList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                regulationService.total(pageReqParam.getFilter()),
                regulationService.selectListItemByCondition(pageReqParam.getFilter()));
    }

    @RequestMapping(value = "/api/console/regulation/list", method = RequestMethod.POST)
    public RespPage consoleGetRegulationList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                regulationService.total(pageReqParam.getFilter()),
                regulationService.selectListItemByCondition(pageReqParam.getFilter()));
   }

    @RequestMapping(value="/api/regulation/content/{name}", method = RequestMethod.GET)
    public void GetRegulationContent(@PathVariable String name, HttpServletResponse response){
       if (regulationService.getRegulationContent(name, response) == false){
           logger.error("GetRegulationContent error");
       }
    }

    @RequestMapping(value = "/api/regulation/upload", method = RequestMethod.POST)
    public RespData FileUpload(@RequestParam(value = "department") Integer departmentId,
                               @RequestParam(value = "issueDate") String issueDateStr,
                               MultipartFile file) throws IllegalArgumentException{
       //检查用户权限
        if (!permissionService.regPermissionExist(sessionService.getCurrentUserId())){
            return RespData.err(RespCode.NO_PERM);
        }
        //转换并检查发布日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date issueDate = null;
        try {
            issueDate = sdf.parse(issueDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("issudate error:" + issueDateStr);
        }        //存储文件
        regulationService.FileUpload(departmentId, issueDate, file);
        return RespData.ok();
    }

    @PostMapping(value = "/api/regulation/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setRegulationState(@RequestBody RegulationListItem regulationListItem){
        System.out.println("setRegulationState:" + regulationListItem.getName());
        Regulation regulation = new Regulation(regulationListItem.getName(),
                regulationListItem.getDepartmentId(),
                regulationListItem.getIssueDate(),
                regulationListItem.getState(),
                regulationListItem.getOperatorId(),
                new Timestamp(System.currentTimeMillis()));
        regulationService.updateState(regulation);




/*    public void setRegulationState(HttpServletRequest request){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                System.out.println(str);
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(sb.toString());
*/

 //       System.out.println("change reg state: " + name + "state: " + state );
 //       if (regulationService.stateValid(state)){
  //          regulationService.updateState(name, state);
  //      }
    }


}
