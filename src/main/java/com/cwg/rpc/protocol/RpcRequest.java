package com.cwg.rpc.protocol;

import java.util.Arrays;

/**
 * 创建时间：2020-08-15 22:55
 * NettyRpc 的 客户端发起请求的 请求对象
 *
 * @author 曹文岗
 **/
public class RpcRequest {

    // 调用编号
    private String requestId;

    // 类名
    private String className;

    // 方法名
    private String methodName;

    // 请求参数的数据类型
    private Class<?>[] parameterTypes;

    // 请求的参数列表
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "requestId='" + requestId + '\n'
                + "className='" + className + '\n'
                + "methodName='" + methodName + '\n'
                + "parameterTypes=" + Arrays.toString(parameterTypes) + '\n'
                + "parameters=" + Arrays.toString(parameters);
    }
}
