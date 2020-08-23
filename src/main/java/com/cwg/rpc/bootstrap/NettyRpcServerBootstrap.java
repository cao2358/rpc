package com.cwg.rpc.bootstrap;

import com.cwg.rpc.server.NettyRPCServer;

/**
 * 创建时间：2020-08-16 17:47
 *
 * @author 曹文岗
 **/
public class NettyRpcServerBootstrap {
    public static void main(String[] args) {

        NettyRPCServer server = new NettyRPCServer();

        server.serverStart();
    }
}
