package com.cwg.rpc.common;

import com.cwg.rpc.client.NettyRPCClient;
import com.cwg.rpc.protocol.RpcRequest;
import com.cwg.rpc.protocol.RpcResponse;

/**
 * 创建时间：2020-08-16 17:46
 *
 * @author 曹文岗
 **/
public class Transporters {
    public static RpcResponse send(RpcRequest request) {

        // 初始化一个客户端
        NettyRPCClient nettyRpcClient = new NettyRPCClient(NettyProperties.REMOTE_HOST, NettyProperties.PORT);

        // 链接服务器
        nettyRpcClient.connect(nettyRpcClient.getHostname(), nettyRpcClient.getPort());

        // 发送消息
        RpcResponse rpcResponse = nettyRpcClient.send(request);

        // 拿回结果
        return rpcResponse;
    }
}
