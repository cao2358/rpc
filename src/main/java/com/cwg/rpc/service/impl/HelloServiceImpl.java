package com.cwg.rpc.service.impl;

import com.cwg.rpc.service.HelloService;

/**
 * 创建时间：2020-08-16 17:44
 *
 * @author 曹文岗
 **/
public class HelloServiceImpl implements HelloService {
    public HelloServiceImpl() {
    }
    @Override
    public String hello(String name) {
        return  "Server: Hello " + name;
    }
}
