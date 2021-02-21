package com.grpetr.task.db.bean;

import java.io.Serializable;

public class Statistic implements Serializable{

    private static final long serialVersionUID = -7252570001581437082L;
    private String userName;
    private String expositionName;
    private String additionalInfo;
    private int cost;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExpositionName() {
        return expositionName;
    }

    public void setExpositionName(String expositionName) {
        this.expositionName = expositionName;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "userName='" + userName + '\'' +
                ", expositionName='" + expositionName + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
