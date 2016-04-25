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
import com.wyc.service.AdGoodHeaderImgService;
import com.wyc.service.MyResourceService;
import com.wyc.smart.service.UploadToQNService;

@Controller
public class AdHeaderImgAction {
    @Autowired
    private AdGoodHeaderImgService adGoodHeaderImgService;
    @Autowired
    private MyResourceService myResourceService;
    @Autowired
    private UploadToQNService uploadToQNService;
    @RequestMapping("/manager/ad_good_header_img")
    public String adGoodHeaderImg(HttpServletRequest httpServletRequest){
        Iterable<SystemAdGoodHeaderImg> adGoodHeaderImgs = adGoodHeaderImgService.findAllOrderByRankAsc();
        httpServletRequest.setAttribute("adGoodHeaders", adGoodHeaderImgs);
        return "system/adGoodHeaderImg";
    }
    
    @RequestMapping("/manager/ad_good_header_img_add_view")
    public String adGoodHeaderImgAddView(HttpServletRequest httpServletRequest){
        return "system/adGoodHeaderImgAdd";
    }
    
    @RequestMapping("/manager/ad_good_header_img_del_do")
    public String addGoodHeaderImgDelDo(HttpServletRequest httpServletRequest){
        String id = httpServletRequest.getParameter("id");
        adGoodHeaderImgService.delete(id);
        return "redirect:/manager/ad_good_header_img";
    }
    @RequestMapping("/manager/ad_good_header_img_add_do")
    public String adGoodHeaderImgAddDo(HttpServletRequest httpServletRequest)throws Exception{
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)httpServletRequest;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("img");
        MyResource myResource = new MyResource();
        String resourceId = UUID.randomUUID().toString();
        myResource.setId(resourceId);
       
        
        
        String url= httpServletRequest.getParameter("url");
        String rank = httpServletRequest.getParameter("rank");
        SystemAdGoodHeaderImg systemAdGoodHeaderImg = new SystemAdGoodHeaderImg();
        
        
        systemAdGoodHeaderImg.setUrl(url);
        systemAdGoodHeaderImg.setRank(Integer.parseInt(rank));
        
        
        if(commonsMultipartFile!=null&&commonsMultipartFile.getOriginalFilename()!=null&&!commonsMultipartFile.getOriginalFilename().trim().equals("")){
            
            String fileName = commonsMultipartFile.getOriginalFilename();
            myResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
            myResource.setName(fileName.substring(0,fileName.lastIndexOf(".")));
            myResourceService.addToWebpath(myResource, commonsMultipartFile.getInputStream());
            uploadToQNService.syncResource(myResource);
            systemAdGoodHeaderImg.setImgUrl(myResource.getUrl());
            
        }
        adGoodHeaderImgService.add(systemAdGoodHeaderImg);
        return "redirect:/manager/ad_good_header_img";
    }
}
