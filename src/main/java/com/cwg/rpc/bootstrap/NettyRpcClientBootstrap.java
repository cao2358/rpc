package com.cwg.rpc.bootstrap;

import com.cwg.rpc.common.ProxyFactory;
import com.cwg.rpc.service.HelloService;

/**
 * 创建时间：2020-08-16 17:43
 *
 * @author 曹文岗
 **/
public class NettyRpcClientBootstrap {
    public static void main(String[] args) {

        HelloService helloServiceProxy = ProxyFactory.create(HelloService.class);

        helloServiceProxy.hello("caowg");
    }
}
