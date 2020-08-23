package com.cwg.rpc.client;

import com.cwg.rpc.protocol.RpcRequest;
import com.cwg.rpc.protocol.RpcResponse;

/**
 * 创建时间：2020-08-16 17:39
 *
 * 定义一个客户端的接口
 * @author 曹文岗
 **/
public interface RpcClient {
    // 发送请求
    RpcResponse send(RpcRequest request);

    // 链接服务器
    void connect(String hostname, int port);

    // 关闭客户端
    void close();
}
