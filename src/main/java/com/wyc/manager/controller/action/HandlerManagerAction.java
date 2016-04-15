package com.wyc.manager.controller.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.domain.SystemPayHandler;
import com.wyc.service.PayHandlerService;

@Controller
public class HandlerManagerAction {
    @Autowired
    private PayHandlerService payHandlerService;
    
    @RequestMapping("/manager/handlers")
    public String handlers(HttpServletRequest httpServletRequest){
        Iterable<SystemPayHandler> systemPayHandlers = payHandlerService.findAll();
        httpServletRequest.setAttribute("handlers", systemPayHandlers);
        return "system/handlers";
    }
    
    @RequestMapping("/manager/handler_add_view")
    public String addHandlerView(HttpServletRequest httpServletRequest){
        return "system/handlerAdd";
    }
    
    @RequestMapping("/manager/handler_update_view")
    public String updateHandlerView(HttpServletRequest httpServletRequest){
        String id = httpServletRequest.getParameter("id");
        SystemPayHandler systemPayHandler = payHandlerService.findOne(id);
        httpServletRequest.setAttribute("handler", systemPayHandler);
        return "system/handlerUpdate";
    }
    
    @RequestMapping("/manager/handler_update_do")
    public String updateHandler(HttpServletRequest httpServletRequest){
        String id = httpServletRequest.getParameter("id");
        SystemPayHandler systemPayHandler = payHandlerService.findOne(id);
        String name = httpServletRequest.getParameter("name");
        String classPath = httpServletRequest.getParameter("class_path");
        systemPayHandler.setClassPath(classPath);
        systemPayHandler.setName(name);
        payHandlerService.save(systemPayHandler);
        return "redirect:/manager/handlers";
    }
    
    
    @RequestMapping("/manager/handler_add_do")
    public String addHandler(HttpServletRequest httpServletRequest){
        String name = httpServletRequest.getParameter("name");
        String classPath = httpServletRequest.getParameter("class_path");
        SystemPayHandler systemPayHandler = new SystemPayHandler();
        systemPayHandler.setClassPath(classPath);
        systemPayHandler.setName(name);
        payHandlerService.add(systemPayHandler);
        return "redirect:/manager/handlers";
    }
}
