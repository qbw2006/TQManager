package com.candy.dao;

import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Maps;

public class RedisResult extends RedisServerEntity {

    /**
     * ip:port组成的全局唯一id
     */
    private String id;

    /**
     * 是否在线
     */
    @JSONField(name = "isAlive")
    private boolean isAlive = false;

    /**
     * 生成时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    /**
     * redis info命令的返回
     */
    private Map<Object, Object> info = Maps.newHashMap();

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public Map<Object, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<Object, Object> info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
