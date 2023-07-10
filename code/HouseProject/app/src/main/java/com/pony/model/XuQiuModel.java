package com.pony.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2021/2/4.
 */

public class XuQiuModel implements Serializable {


    /**
     * xuqiuMoney : 1001-1500
     * xuqiuId : 1
     * xuqiuAddress : 西安市碑林区
     * xuqiuMianJi : 51-70
     * xuqiuUserId : 19
     */

    private String xuqiuMoney;
    private int xuqiuId;
    private String xuqiuAddress;
    private String xuqiuMianJi;
    private int xuqiuUserId;

    public String getXuqiuMoney() {
        return xuqiuMoney;
    }

    public void setXuqiuMoney(String xuqiuMoney) {
        this.xuqiuMoney = xuqiuMoney;
    }

    public int getXuqiuId() {
        return xuqiuId;
    }

    public void setXuqiuId(int xuqiuId) {
        this.xuqiuId = xuqiuId;
    }

    public String getXuqiuAddress() {
        return xuqiuAddress;
    }

    public void setXuqiuAddress(String xuqiuAddress) {
        this.xuqiuAddress = xuqiuAddress;
    }

    public String getXuqiuMianJi() {
        return xuqiuMianJi;
    }

    public void setXuqiuMianJi(String xuqiuMianJi) {
        this.xuqiuMianJi = xuqiuMianJi;
    }

    public int getXuqiuUserId() {
        return xuqiuUserId;
    }

    public void setXuqiuUserId(int xuqiuUserId) {
        this.xuqiuUserId = xuqiuUserId;
    }
}
