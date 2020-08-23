package com.cwg.rpc.common;

import com.cwg.rpc.protocol.RpcRequest;
import com.cwg.rpc.protocol.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 创建时间：2020-08-16 17:45
 *
 * @author 曹文岗
 **/
public class RpcInvoker<T> implements InvocationHandler {
    private Class<T> clazz;

    public RpcInvoker(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("代理方法开始执行");
        /**
         * 构建 RpcRequest 对象
         */
        RpcRequest request = new RpcRequest();

        // 获取参数
        String requestId = UUID.randomUUID().toString();
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();

        /**
         * 组装 RpcRequest 请求对象
         */
        request.setRequestId(requestId);
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameterTypes(parameterTypes);
        request.setParameters(args);

        System.out.println(request.toString());
        /**
         * 发送请求
         */
        RpcResponse rpcResponse = Transporters.send(request);
        return rpcResponse.getResult();
    }
}
