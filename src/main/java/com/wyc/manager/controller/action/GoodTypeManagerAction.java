package com.wyc.manager.controller.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wyc.domain.MyResource;
import com.wyc.domain.SystemGoodType;
import com.wyc.service.GoodTypeService;
import com.wyc.service.MyResourceService;
import com.wyc.smart.service.UploadToQNService;

@Controller
public class GoodTypeManagerAction {
    @Autowired
    private GoodTypeService goodTypeService;
    @Autowired
    private MyResourceService myResourceServcie;
    @Autowired
    private UploadToQNService uploadToQNService;
    @RequestMapping("/manager/good_type_add_view")
    public String addGoodView(HttpServletRequest httpServletRequest){
        
        return "system/goodTypeAdd";
    }
    
    private Map<String, Object> responseGoodType(SystemGoodType goodType){
        Map<String, Object> responseType = new HashMap<String, Object>();
        responseType.put("createAt", goodType.getCreateAt());
        responseType.put("img", goodType.getImg());
        responseType.put("name", goodType.getName());
        responseType.put("title", goodType.getTitle());
        responseType.put("id",goodType.getId());
        responseType.put("isDefault", goodType.isDefault());
        responseType.put("isDisplay", goodType.getIsDisplay());
        return responseType;
    }
    
    @RequestMapping("/manager/good_type_update_view")
    public String updateGoodTypeView(HttpServletRequest httpServletRequest){
        String id = httpServletRequest.getParameter("id");
        SystemGoodType goodType = goodTypeService.findOne(id);
        Map<String, Object> responseGoodType = responseGoodType(goodType);
        httpServletRequest.setAttribute("goodType",responseGoodType);
        return "system/goodTypeUpdate";
    }
    
    @RequestMapping("/manager/good_type_update_do")
    public String updateGoodTypeDo(MultipartHttpServletRequest httpServletRequest)throws Exception{
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)httpServletRequest;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("img");
        String name = multipartHttpServletRequest.getParameter("name");
        String title = multipartHttpServletRequest.getParameter("title");
        String idDefault = multipartHttpServletRequest.getParameter("idDefault");
        String id = multipartHttpServletRequest.getParameter("id");
        String isDisplay = multipartHttpServletRequest.getParameter("isDisplay");
        SystemGoodType goodType = goodTypeService.findOne(id);
        goodType.setName(name);
        goodType.setTitle(title);
        goodType.setDefault(Boolean.parseBoolean(idDefault));
        goodType.setIsDisplay(Integer.parseInt(isDisplay));
        MyResource myResource = new MyResource();
        String resourceId = UUID.randomUUID().toString();
        myResource.setId(resourceId);
        if(commonsMultipartFile!=null&&commonsMultipartFile.getOriginalFilename()!=null&&!commonsMultipartFile.getOriginalFilename().trim().equals("")){
            
            String fileName = commonsMultipartFile.getOriginalFilename();
            myResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
            myResource.setName(fileName.substring(0,fileName.lastIndexOf(".")));
            myResourceServcie.addToWebpath(myResource, commonsMultipartFile.getInputStream());
            uploadToQNService.syncResource(myResource);
            goodType.setImg(myResource.getUrl());
        }
        
        
        goodType = goodTypeService.save(goodType);
        return "redirect:/manager/good_type_view";
    }
    
    @RequestMapping("/manager/good_type_add_do")
    public String addGoodTypeDo(MultipartHttpServletRequest httpServletRequest)throws Exception{
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)httpServletRequest;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("img");
        String name = multipartHttpServletRequest.getParameter("name");
        String title = multipartHttpServletRequest.getParameter("title");
        String idDefault = multipartHttpServletRequest.getParameter("idDefault");
        MyResource myResource = new MyResource();
        String resourceId = UUID.randomUUID().toString();
        myResource.setId(resourceId);
        if(commonsMultipartFile!=null&&commonsMultipartFile.getOriginalFilename()!=null&&!commonsMultipartFile.getOriginalFilename().trim().equals("")){
            
            String fileName = commonsMultipartFile.getOriginalFilename();
            myResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
            myResource.setName(fileName.substring(0,fileName.lastIndexOf(".")));
            
        }
        myResourceServcie.addToWebpath(myResource, commonsMultipartFile.getInputStream());
        uploadToQNService.syncResource(myResource);
        SystemGoodType goodType = new SystemGoodType();
        goodType.setDefault(Boolean.parseBoolean(idDefault));
        goodType.setImg(myResource.getUrl());
        goodType.setName(name);
        goodType.setTitle(title);
        goodType = goodTypeService.add(goodType);
        return "redirect:/manager/good_type_view";

    }
    
    @RequestMapping("/manager/good_type_view")
    public String goodTypeView(HttpServletRequest httpServletRequest){
        List<Map<String, Object>> responseTypes = new ArrayList<Map<String,Object>>();
        Iterable<SystemGoodType> goodTypes = goodTypeService.findAll();
        for(SystemGoodType goodType:goodTypes){
             Map<String, Object> responseType = responseGoodType(goodType);
            responseTypes.add(responseType);
        }
        httpServletRequest.setAttribute("goodTypes", responseTypes);
        return "system/goodTypes";
    }
}
