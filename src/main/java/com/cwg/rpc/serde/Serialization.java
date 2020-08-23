package com.cwg.rpc.serde;

/**
 * 创建时间：2020-08-15 23:07
 *
 * @author 曹文岗
 **/
public interface Serialization {
    // 序列化方法
    <T> byte[] serialize(T obj);

    // 反序列化方法
    <T> T deSerialize(byte[] data, Class<T> clz);
}
