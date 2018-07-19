package com.liu.orderservice.service;

import com.liu.orderservice.utils.RestTemplateUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/18.
 */
@Component("invokeOtherService")
public class InvokeOtherService {

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    // 同步创建用户
    @HystrixCommand(fallbackMethod = "createFallback")
    public String createUser(Map param,HttpServletRequest request) {
        try {
            System.out.println("创建用户传入参数：" + param!=null?param.toString():"");
            String resp = restTemplateUtil.post("http://com-liu-userservice/userservice/api",param,request);
            System.out.println("创建用户返回：" + resp);
            return resp;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    // post失败回调方法
    public String createFallback(Map param,HttpServletRequest request) {
        System.out.println("创建用户HystrixCommand异常：" + param);
        return "201";
    }
}
