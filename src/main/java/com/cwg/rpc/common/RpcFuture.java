package com.cwg.rpc.common;

import com.cwg.rpc.protocol.RpcResponse;

/**
 * 创建时间：2020-08-15 23:00
 * 定义了一个用来管理 Response 的类
 * 通过异步的方式获取响应结果
 *
 * @author 曹文岗
 **/
public class RpcFuture {

    // 响应结果
    private RpcResponse rpcResponse;

    // 是否已经能获取到结果
    private volatile boolean isResponseSet = false;

    // 锁对象
    private final Object lock = new Object();

    /**
     * @param timeout
     * @return
     * 获取结果
     */
    public RpcResponse getResponse(int timeout) {
        synchronized (lock) {
            while (!isResponseSet) {
                try {
                    //wait
                    lock.wait(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return rpcResponse;
        }
    }

    /**
     *  设置结果
     * @param response
     */
    public void setResponse(RpcResponse response) {
        if (isResponseSet) {
            return;
        }
        synchronized (lock) {
            this.rpcResponse = response;
            this.isResponseSet = true;
            //notiy
            lock.notify();
        }
    }
}
