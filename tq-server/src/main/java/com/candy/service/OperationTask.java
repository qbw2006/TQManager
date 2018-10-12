package com.candy.service;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotBlank;

public class OperationTask {

    // 0:stop 1:start
    @Max(1)
    private int opertion;

    @NotBlank
    private String id;

    public int getOpertion() {
        return opertion;
    }

    public void setOpertion(int opertion) {
        this.opertion = opertion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RedisTask [opertion=" + opertion + ", id=" + id + "]";
    }
}
