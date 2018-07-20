package com.liu.orderservice.controller;

import com.liu.orderservice.domain.UserEntity;
import com.liu.orderservice.service.InvokeOtherService;
import com.liu.orderservice.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/12.
 */
@RestController
public class TestController {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private InvokeOtherService invokeOtherService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value="/orderservice")
    @ResponseBody
    public String goUploadImg(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("user","111");
        String re = invokeOtherService.createUser(map,request);//一个
        return re;
    }

    @PostMapping(value="/send")
    public String send(String name) {

        kafkaTemplate.send("TextLinesTopic",name);
        return "succeed";
    }

    @PostMapping(value="/user")
    public String user() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("test");
        userEntity.setPassWord("123");
        userEntity.setUserSex("1");
        userMapper.insert(userEntity);
        return "succeed";
    }



}
