package com.cwg.rpc.handler;

import com.cwg.rpc.protocol.RpcRequest;
import com.cwg.rpc.protocol.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;

/**
 * 创建时间：2020-08-15 22:53
 *
 * @author 曹文岗
 **/
public class NettyRpcServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        channelRead0(ctx, msg);
    }

    private void channelRead0(ChannelHandlerContext ctx, Object msg) {

        RpcResponse rpcResponse = new RpcResponse();

        RpcRequest request = (RpcRequest) msg;
        rpcResponse.setRequestId(request.getRequestId());

        try {
            // 收到请求后开始处理请求
            Object handler = handler(request);
            rpcResponse.setResult(handler);

        } catch (Throwable throwable) {

            // 如果抛出异常也将异常存入 response 中
            rpcResponse.setThrowable(throwable);
            throwable.printStackTrace();
        }
        // 操作完以后写入 netty 的上下文中。netty 自己处理返回值。
        ctx.writeAndFlush(rpcResponse);
    }

    private Object handler(RpcRequest request) throws ClassNotFoundException, InvocationTargetException,
            IllegalAccessException, InstantiationException {

        // 通过反射创建 服务类 实例
        Class<?> clz = Class.forName(request.getClassName());
        Object serviceBean = clz.newInstance();

        // 通过反射创建 服务类的 方法实例
        Class<?> serviceClass = clz.getClass();
        String methodName = request.getMethodName();

        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        // 根本思路还是获取类名和方法名，利用反射实现调用
        FastClass fastClass = FastClass.create(serviceClass);
        FastMethod fastMethod = fastClass.getMethod(methodName, parameterTypes);

        // 实际调用发生的地方
        return fastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("------------------");
        ctx.close();
    }
}
