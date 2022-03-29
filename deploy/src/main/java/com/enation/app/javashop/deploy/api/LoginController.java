/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.api;

import com.enation.app.javashop.deploy.model.Certificate;
import com.enation.app.javashop.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kingapex on 23/02/2018.
 * 登录控制器
 * @author kingapex
 * @version 1.0
 * @since 6.4.0
 * 23/02/2018
 */
@RestController
@RequestMapping("/user")
public class LoginController {


    @Autowired
    private Certificate certificate;

    @PostMapping("/login")
    public  void login(String username,String password){

        if (certificate.getPassword().equals(password) && certificate.getUsername().equals(username)) {

        }else {
            throw  new ServiceException("403","用户名密码错误");
        }

    }


}
