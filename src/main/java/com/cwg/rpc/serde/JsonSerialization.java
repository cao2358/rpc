package com.cwg.rpc.serde;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 创建时间：2020-08-15 23:06
 *
 * @author 曹文岗
 **/
public class JsonSerialization implements Serialization {
    private ObjectMapper objectMapper;

    public JsonSerialization() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * TODO_MA json序列化方法
     * @param obj
     * @param <T>
     * @return
     */
    public <T> byte[] serialize(T obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * TODO_MA json反序列化方法
     * @param data
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T deSerialize(byte[] data, Class<T> clz) {
        try {
            return objectMapper.readValue(data, clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
