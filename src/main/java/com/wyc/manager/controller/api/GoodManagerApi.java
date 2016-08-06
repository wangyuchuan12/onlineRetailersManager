package com.wyc.manager.controller.api;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.wyc.defineBean.ApiResponse;
import com.wyc.domain.Good;
import com.wyc.domain.GoodDistribution;
import com.wyc.domain.GoodImg;
import com.wyc.domain.MyResource;
import com.wyc.domain.Source;
import com.wyc.service.CityService;
import com.wyc.service.GoodDistributionService;
import com.wyc.service.GoodImgService;
import com.wyc.service.GoodService;
import com.wyc.service.MyResourceService;
import com.wyc.service.SourceService;
import com.wyc.smart.service.UploadToQNService;

@RestController
public class GoodManagerApi {
    @Autowired
    private GoodService goodService;
    @Autowired
    private MyResourceService resourceService;
    @Autowired
    private GoodImgService goodImgService;
    @Autowired
    private SourceService sourceService;
    @Autowired
    private GoodDistributionService goodDistributionService;
    @Autowired
    private CityService cityService;
    @Column
    private UploadToQNService uploadToQNService;
   
}
