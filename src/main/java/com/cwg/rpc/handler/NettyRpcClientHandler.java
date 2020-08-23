package com.cwg.rpc.handler;

import com.cwg.rpc.common.RpcFuture;
import com.cwg.rpc.protocol.RpcRequest;
import com.cwg.rpc.protocol.RpcResponse;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建时间：2020-08-15 22:59
 *
 * @author 曹文岗
 **/
public class NettyRpcClientHandler extends ChannelDuplexHandler {
    // 使用 map 维护 id 和 Future 的映射关系，在多线程环境下需要使用线程安全的容器
    private final Map<String, RpcFuture> futureMap = new ConcurrentHashMap<>();

    /**
     * 
     * @param ctx
     * @param msg
     * @param promise
     * @throws Exception
     */
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof RpcRequest) {
            RpcRequest request = (RpcRequest) msg;
            // 写数据的时候，增加映射
            futureMap.putIfAbsent(request.getRequestId(), new RpcFuture());
        }
        super.write(ctx, msg, promise);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RpcResponse) {
            RpcResponse response = (RpcResponse) msg;
            // 获取数据的时候 将结果放入 future 中
            RpcFuture defaultFuture = futureMap.get(response.getRequestId());
            defaultFuture.setResponse(response);
        }
        super.channelRead(ctx, msg);
    }

    /**
     *  根据 requestID 获取 RpcResponse
     * @param requestId
     * @return
     */
    public RpcResponse getRpcResponse(String requestId) {
        try {
            // 从 future 中获取真正的结果。
            RpcFuture defaultFuture = futureMap.get(requestId);
            return defaultFuture.getResponse(10);
        } finally {
            // 完成后从 map 中移除。
            futureMap.remove(requestId);
        }
    }
}
