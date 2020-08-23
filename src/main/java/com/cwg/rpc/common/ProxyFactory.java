package com.cwg.rpc.common;

import java.lang.reflect.Proxy;

/**
 * 创建时间：2020-08-16 17:44
 *
 * @author 曹文岗
 **/
public class ProxyFactory {
    /**
     * @param interfaceClass
     * @param <T>
     * @return 根据参数类型，创建对应的代理对象
     */
    public static <T> T create(Class<T> interfaceClass) {

        // interfaceClass 必须是一个接口！
        return (T) Proxy.newProxyInstance(interfaceClass
                .getClassLoader(), new Class<?>[]{interfaceClass}, new RpcInvoker<T>(interfaceClass));
    }
}
