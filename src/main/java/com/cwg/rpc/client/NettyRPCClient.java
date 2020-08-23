package com.cwg.rpc.client;

import com.cwg.rpc.codec.RpcDecoder;
import com.cwg.rpc.codec.RpcEncoder;
import com.cwg.rpc.handler.NettyRpcClientHandler;
import com.cwg.rpc.protocol.RpcRequest;
import com.cwg.rpc.protocol.RpcResponse;
import com.cwg.rpc.serde.JsonSerialization;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 创建时间：2020-08-16 17:40
 *
 * @author 曹文岗
 **/
public class NettyRPCClient implements RpcClient {
    private String hostname;
    private int port;

    private Channel channel;
    private NettyRpcClientHandler clientHandler;
    private NioEventLoopGroup eventLoopGroup;

    public NettyRPCClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    @Override
    public RpcResponse send(RpcRequest request) {
        try {

            // 发送请求
            channel.writeAndFlush(request).await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 根据 requestID 来获取 RpcResponse
        return clientHandler.getRpcResponse(request.getRequestId());
    }

    @Override
    public void connect(String hostname, int port) {
        clientHandler = new NettyRpcClientHandler();
        eventLoopGroup = new NioEventLoopGroup();

        Bootstrap clientBootstrap = new Bootstrap();

        clientBootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(initChannelInitializer(clientHandler));

        try {

            // 启动客户端
            channel = clientBootstrap.connect(hostname, port).sync().channel();

        } catch (InterruptedException e) {
            System.out.println("Netty RPC Client start Error !!!! ");
            e.printStackTrace();
        }
    }

    private ChannelInitializer initChannelInitializer(NettyRpcClientHandler clientHandler) {

        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,4));
                pipeline.addLast(new RpcEncoder(RpcRequest.class,new JsonSerialization()));
                pipeline.addLast(new RpcDecoder(RpcResponse.class,new JsonSerialization()));
                pipeline.addLast(clientHandler);
            }
        };
    }
    @Override
    public void close() {
        eventLoopGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
    }
}
