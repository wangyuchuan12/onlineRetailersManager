package com.wyc.manager.task;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.service.TokenService;
import com.wyc.wx.domain.Token;

public class ClearInvalidTokenTask {
    @Autowired
    private TokenService tokenService;
    final static Logger logger = LoggerFactory.getLogger(ClearInvalidTokenTask.class);
    public void run() {
        logger.debug("run clearInvalidTokenTask");
        Iterable<Token> tokens = tokenService.findAllByInvalidDateLessThan(new DateTime());
        for(Token token:tokens){
            logger.debug("delete token id is {}",token.getId());
            tokenService.delete(token.getId());
        }

    }
}
