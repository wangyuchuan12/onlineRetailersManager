package com.wyc.manager.controller.action;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wyc.domain.Business;
import com.wyc.domain.MyResource;
import com.wyc.manager.controller.AdminController;
import com.wyc.manager.domain.Admin;
import com.wyc.manager.service.AdminService;
import com.wyc.service.BusinessService;
import com.wyc.service.MyResourceService;
import com.wyc.smart.service.UploadToQNService;

@Controller
public class BusinessInfoAction {
    @Autowired
    private AdminService adminService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private MyResourceService myResourceService;
    @Autowired
    private UploadToQNService uploadToQNService;
    final static Logger logger = LoggerFactory.getLogger(BusinessInfoAction.class);
    @RequestMapping("/manager/business_info")
    public String businessInfo(HttpServletRequest httpServletRequest){
        Subject subject = SecurityUtils.getSubject();
        Admin admin = adminService.findByUsername((String)subject.getPrincipal());
        Business business = businessService.findByAdminId(admin.getId()+"");
        httpServletRequest.setAttribute("admin", admin);
        httpServletRequest.setAttribute("business", business);
        return "businessInfo/businessInfo";
    }
    @Transactional
    @RequestMapping("/manager/update_business_info")
    public String updateBusinessInfo(MultipartHttpServletRequest httpServletRequest)throws Exception{
        Subject subject = SecurityUtils.getSubject();
        Admin admin = adminService.findByUsername((String)subject.getPrincipal());
        Business business = businessService.findByAdminId(admin.getId()+"");
        String realname = httpServletRequest.getParameter("realname");
        String mobile = httpServletRequest.getParameter("mobile");
        String email = httpServletRequest.getParameter("email");
        admin.setRealname(realname);
        admin.setMobile(mobile);
        admin.setEmail(email);
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)httpServletRequest;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("img");
        String businessName = httpServletRequest.getParameter("business_name");
        String businessInstruction = httpServletRequest.getParameter("business_instruction");
        business.setName(businessName);
        business.setInstruction(businessInstruction);
        if(commonsMultipartFile!=null&&commonsMultipartFile.getOriginalFilename()!=null&&!commonsMultipartFile.getOriginalFilename().trim().equals("")){
            MyResource myResource = new MyResource();
            String resourceId = UUID.randomUUID().toString();
            myResource.setId(resourceId);
            String fileName = commonsMultipartFile.getOriginalFilename();
            myResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
            myResource.setName(fileName.substring(0,fileName.lastIndexOf(".")));
            try {
                myResourceService.addToWebpath(myResource, commonsMultipartFile.getInputStream());
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw e;
            }
            uploadToQNService.syncResource(myResource);
            business.setImgUrl(myResource.getUrl());
        }
        adminService.save(admin);
        businessService.save(business);
        return "redirect:/manager/business_info";
    }
}
