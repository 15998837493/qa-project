
package com.qa.main.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mnt.health.utils.thread.TokenManager;
import com.qa.main.utils.PublicConfig;
import com.qa.system.pojo.LoginUser;
import com.qa.system.service.LoginService;

/**
 * Service公共类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-4 zcq 初版
 */
@Service
public abstract class BaseService {

    @Resource
    public PublicConfig publicConfig;

    @Resource
    protected LoginService loginService;

    public LoginUser getLoginUser() {
        String token = TokenManager.getCurrHashMap().get(Thread.currentThread());
        return loginService.getLogin(token);
    }

    public String getInsId() {
        String insId = "0";
        // LoginUser loginUser = this.getLoginUser();
        // if (loginUser != null) {
        // insId = loginUser.getUserPojo().getCreateInsId();
        // }
        return insId;
    }

}
