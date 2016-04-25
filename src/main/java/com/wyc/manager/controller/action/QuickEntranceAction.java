package com.wyc.manager.controller.action;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wyc.domain.MyResource;
import com.wyc.domain.SystemAdGoodHeaderImg;
import com.wyc.domain.SystemQuickEntrance;
import com.wyc.service.MyResourceService;
import com.wyc.service.QuickEntranceService;
import com.wyc.smart.service.UploadToQNService;

@Controller
public class QuickEntranceAction {
    @Autowired
    private QuickEntranceService quickEntranceServcie;
    @Autowired
    private MyResourceService myResourceServcie;
    @Autowired
    private UploadToQNService uploadToQNService;
    @RequestMapping("/manager/quick_entrance")
    public String quickEntrance(HttpServletRequest httpServletRequest){
        Iterable<SystemQuickEntrance> quickEntrances = quickEntranceServcie.findAllOrderByRankAsc();
        httpServletRequest.setAttribute("quickEntrances", quickEntrances);
        return "system/quickEntrance";
    }
    
    
    @RequestMapping("/manager/quick_entrance_del_do")
    public String addQuickEntranceDelDo(HttpServletRequest httpServletRequest){
        String id = httpServletRequest.getParameter("id");
        quickEntranceServcie.delete(id);
        return "redirect:/manager/quick_entrance";
    }
    
    @RequestMapping("/manager/quick_entrance_add_do")
    public String quickEntranceAddDo(HttpServletRequest httpServletRequest)throws Exception{
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)httpServletRequest;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("img");
        MyResource myResource = new MyResource();
        String resourceId = UUID.randomUUID().toString();
        myResource.setId(resourceId);
       
        
        
        String url= httpServletRequest.getParameter("url");
        String rank = httpServletRequest.getParameter("rank");
        String name = httpServletRequest.getParameter("name");
        SystemQuickEntrance systemQuickEntrance = new SystemQuickEntrance();
        
        systemQuickEntrance.setName(name);
        systemQuickEntrance.setUrl(url);
        systemQuickEntrance.setRank(Integer.parseInt(rank));
        
        
        if(commonsMultipartFile!=null&&commonsMultipartFile.getOriginalFilename()!=null&&!commonsMultipartFile.getOriginalFilename().trim().equals("")){
            
            String fileName = commonsMultipartFile.getOriginalFilename();
            myResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
            myResource.setName(fileName.substring(0,fileName.lastIndexOf(".")));
            myResourceServcie.addToWebpath(myResource, commonsMultipartFile.getInputStream());
            uploadToQNService.syncResource(myResource);
            systemQuickEntrance.setImgUrl(myResource.getUrl());
            
        }
        quickEntranceServcie.add(systemQuickEntrance);
        return "redirect:/manager/quick_entrance";
    }
    
    
    @RequestMapping("/manager/quick_entrance_add_view")
    public String quickEntranceAddView(HttpServletRequest httpServletRequest){
        return "system/quickEntranceAdd";
    }
}
