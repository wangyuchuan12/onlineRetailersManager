package com.wyc.manager.controller.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wyc.domain.Good;
import com.wyc.domain.GoodType;
import com.wyc.domain.MyResource;
import com.wyc.manager.domain.Admin;
import com.wyc.manager.service.AdminService;
import com.wyc.service.GoodService;
import com.wyc.service.GoodTypeService;
import com.wyc.service.MyResourceService;
import com.wyc.smart.service.UploadToQNService;

@Controller
public class GoodManagerAction {
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private MyResourceService myResourceService;
    @Autowired
    private GoodTypeService goodTypeService;
    @Autowired
    private UploadToQNService uploadToQNService;
    final static Logger logger = LoggerFactory.getLogger(GoodManagerAction.class);
    
    public Map<String, Object> responseGood(Good good){
        Map<String, Object> responseGood = new HashMap<String, Object>();
        responseGood.put("id", good.getId());
        responseGood.put("adminId", good.getAdminId());
        responseGood.put("goodInfoHeadImg", good.getGoodInfoHeadImg());
        responseGood.put("goodType", good.getGoodType());
        responseGood.put("headImg", good.getHeadImg());
        if(good.getHeadImg()!=null){
            MyResource headImgResource = myResourceService.findOne(good.getHeadImg());
            responseGood.put("headImgUrl", headImgResource.getUrl());
        }
        responseGood.put("instruction", good.getInstruction());
        responseGood.put("name", good.getName());
        responseGood.put("notice", good.getNotice());
        responseGood.put("title", good.getTitle());
        responseGood.put("aloneDiscount", good.getAloneDiscount());
        responseGood.put("aloneOriginalCost", good.getAloneOriginalCost());
        responseGood.put("couponCost", good.getCouponCost());
        responseGood.put("createAt", good.getCreateAt());
        responseGood.put("flowPrice", good.getFlowPrice());
        responseGood.put("groupDiscount", good.getGroupDiscount());
        responseGood.put("groupDuration", good.getGroupDuration());
        responseGood.put("groupNum", good.getGroupNum());
        responseGood.put("groupOriginalCost", good.getGroupOriginalCost());
        responseGood.put("marketPrice", good.getMarketPrice());
        responseGood.put("rank", good.getRank());
        responseGood.put("salesVolume", good.getSalesVolume());
        responseGood.put("isDisplayMain", good.isDisplayMain()+"");
        responseGood.put("status", good.getStatus());
        responseGood.put("stock", good.getStock());
        responseGood.put("timeLong", good.getTimeLong());
        responseGood.put("updateAt", good.getUpdateAt());
        return responseGood;
    }
    @RequestMapping("/manager/goods")
    public String managerGoods(HttpServletRequest httpServletRequest){
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
        Iterable<Good> goods = goodService.findAllByAdminId(admin.getId()+"");
        List<Map<String, Object>> responseGoods = new ArrayList<Map<String,Object>>();
        for(Good good:goods){
            Map<String, Object> responseGood = responseGood(good);
            responseGoods.add(responseGood);
           
        }
        Iterable<GoodType> goodTypes = goodTypeService.findAll();
        httpServletRequest.setAttribute("goods", responseGoods);
        httpServletRequest.setAttribute("goodTypes", goodTypes);
        return "good/goods";
    }
    
    @RequestMapping("/manager/good_add_view")
    public String managerGoodAdd(HttpServletRequest httpServletRequest){
        Iterable<GoodType> goodTypes = goodTypeService.findAll();
        httpServletRequest.setAttribute("goodTypes", goodTypes);
        return "good/good_add";
    }
    
    @RequestMapping("/manager/good_update_view")
    public String managerGoodUpdate(HttpServletRequest httpServletRequest){
        String goodId = httpServletRequest.getParameter("id");
        Good good = goodService.findOne(goodId);
        httpServletRequest.setAttribute("good",responseGood(good));
        Iterable<GoodType> goodTypes = goodTypeService.findAll();
        httpServletRequest.setAttribute("goodTypes", goodTypes);
        return "good/good_update";
    }
    
    @RequestMapping("/manager/good_del_do")
    public String managerGoodDelDo(HttpServletRequest httpServletRequest){
        String goodId = httpServletRequest.getParameter("id");
        goodService.delete(goodId);
        return "redirect:/manager/goods";
    }
    
    @RequestMapping("/manager/good_update_do")
    public String managerGoodUpdateDo(MultipartHttpServletRequest httpServletRequest){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)httpServletRequest;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("headImg");
        String id = httpServletRequest.getParameter("id");
        String goodType = httpServletRequest.getParameter("goodType");
        logger.debug("goodType:{}",goodType);
        String instruction = httpServletRequest.getParameter("instruction");
        String name = httpServletRequest.getParameter("name");
        String notice = httpServletRequest.getParameter("notice");
        String title = httpServletRequest.getParameter("title");
        String aloneDiscount = httpServletRequest.getParameter("aloneDiscount");
        String aloneOriginalCost = httpServletRequest.getParameter("aloneOriginalCost");
        String couponCost = httpServletRequest.getParameter("couponCost");
        String flowPrice = httpServletRequest.getParameter("flowPrice");
        String groupDiscount = httpServletRequest.getParameter("groupDiscount");
//        String groupDuration = httpServletRequest.getParameter("groupDuration");
        String groupNum = httpServletRequest.getParameter("groupNum");
        String groupOriginalCost = httpServletRequest.getParameter("groupOriginalCost");
        String marketPrice = httpServletRequest.getParameter("marketPrice");
        String rank = httpServletRequest.getParameter("rank");
        String salesVolume = httpServletRequest.getParameter("salesVolume");
        String isDisplayMain = httpServletRequest.getParameter("isDisplay");
        String status = httpServletRequest.getParameter("status");
        logger.debug("status:{}",status);
        logger.debug("isDisplayMain:{}",isDisplayMain);
        String stock = httpServletRequest.getParameter("stock");
        String timeLong = httpServletRequest.getParameter("timeLong");
        
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
        Good good = goodService.findOne(id);
        if(!good.getAdminId().toString().equals(admin.getId().toString())){
            return null;
        }
        
        good.setAloneDiscount(Float.parseFloat(aloneDiscount));
        good.setAloneOriginalCost(Float.parseFloat(aloneOriginalCost));
        good.setCouponCost(1);
        good.setDisplayMain(Boolean.parseBoolean(isDisplayMain));
        good.setFlowPrice(Float.parseFloat(flowPrice));
        good.setGoodType(goodType);
        good.setGroupDiscount(Float.parseFloat(groupDiscount));
        good.setGroupNum(Integer.parseInt(groupNum));
        good.setGroupOriginalCost(Float.parseFloat(groupOriginalCost));
        good.setInstruction(instruction);
        good.setMarketPrice(Float.parseFloat(marketPrice));
        good.setName(name);
        good.setNotice(notice);
        good.setRank(Integer.parseInt(rank));
        if(salesVolume!=null){
            good.setSalesVolume(Long.parseLong(salesVolume));
        }
        good.setStatus(Integer.parseInt(status));
        good.setStock(Long.parseLong(stock));
        good.setTimeLong(Integer.parseInt(timeLong));
        good.setTitle(title);
        
        String fileName = commonsMultipartFile.getOriginalFilename();
        if(commonsMultipartFile!=null&&commonsMultipartFile.getOriginalFilename()!=null&&!commonsMultipartFile.getOriginalFilename().trim().equals("")){
            MyResource myResource = new MyResource();
            String resourceId = UUID.randomUUID().toString();
            myResource.setId(resourceId);
            myResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
            myResource.setName(fileName.substring(0,fileName.lastIndexOf(".")));
            good.setHeadImg(resourceId);
            try {
                myResourceService.addToWebpath(myResource, commonsMultipartFile.getInputStream());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            uploadToQNService.syncResource(myResource);
        }
        
        goodService.save(good);
        
        return "redirect:/manager/goods";
    }
    @RequestMapping("/manager/good_add_do")
    public String managerGoodAddDo(MultipartHttpServletRequest httpServletRequest){
//        String id = httpServletRequest.getParameter("id");
        
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)httpServletRequest;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("headImg");
        String goodType = httpServletRequest.getParameter("goodType");
        String instruction = httpServletRequest.getParameter("instruction");
        String name = httpServletRequest.getParameter("name");
        String notice = httpServletRequest.getParameter("notice");
        String title = httpServletRequest.getParameter("title");
        String aloneDiscount = httpServletRequest.getParameter("aloneDiscount");
        String aloneOriginalCost = httpServletRequest.getParameter("aloneOriginalCost");
        String couponCost = httpServletRequest.getParameter("couponCost");
        String flowPrice = httpServletRequest.getParameter("flowPrice");
        String groupDiscount = httpServletRequest.getParameter("groupDiscount");
//        String groupDuration = httpServletRequest.getParameter("groupDuration");
        String groupNum = httpServletRequest.getParameter("groupNum");
        String groupOriginalCost = httpServletRequest.getParameter("groupOriginalCost");
        String marketPrice = httpServletRequest.getParameter("marketPrice");
        String rank = httpServletRequest.getParameter("rank");
        String salesVolume = httpServletRequest.getParameter("salesVolume");
        String isDisplayMain = httpServletRequest.getParameter("isDisplayMain");
        String status = httpServletRequest.getParameter("status");
        String stock = httpServletRequest.getParameter("stock");
        String timeLong = httpServletRequest.getParameter("timeLong");
        
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
        Good good = new Good();
        
        good.setAdminId(admin.getId()+"");
        System.out.println(aloneDiscount);
        good.setAloneDiscount(Float.parseFloat(aloneDiscount));
        good.setAloneOriginalCost(Float.parseFloat(aloneOriginalCost));
        good.setCouponCost(1);
        good.setDisplayMain(Boolean.parseBoolean(isDisplayMain));
        good.setFlowPrice(Float.parseFloat(flowPrice));
        good.setGoodType(goodType);
        good.setGroupDiscount(Float.parseFloat(groupDiscount));
        good.setGroupNum(Integer.parseInt(groupNum));
        good.setGroupOriginalCost(Float.parseFloat(groupOriginalCost));
        good.setInstruction(instruction);
        good.setMarketPrice(Float.parseFloat(marketPrice));
        good.setName(name);
        good.setNotice(notice);
        good.setRank(Integer.parseInt(rank));
        good.setStatus(Integer.parseInt(status));
        good.setStock(Long.parseLong(stock));
        good.setTimeLong(Integer.parseInt(timeLong));
        good.setTitle(title);
        MyResource myResource = new MyResource();
        String resourceId = UUID.randomUUID().toString();
        myResource.setId(resourceId);
        String fileName = commonsMultipartFile.getOriginalFilename();
        myResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
        myResource.setName(fileName.substring(0,fileName.lastIndexOf(".")));
        good.setHeadImg(resourceId);
        goodService.add(good);
        try {
            myResourceService.addToWebpath(myResource, commonsMultipartFile.getInputStream());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        uploadToQNService.syncResource(myResource);
        return "redirect:/manager/goods";
    }
}
