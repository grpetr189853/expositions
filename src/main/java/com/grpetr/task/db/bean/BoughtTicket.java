package com.grpetr.task.db.bean;

import java.io.Serializable;
import java.time.LocalDate;

public class BoughtTicket implements Serializable {

    private static final long serialVersionUID = -6336630430723129004L;

    private LocalDate dateIn;
    private LocalDate dateOut;
    private int expositionId;
    private String expositionTheme;
    private String additionalInfo;
    private int cost;

    public LocalDate getDateIn() {
        return dateIn;
    }

    public void setDateIn(LocalDate dateIn) {
        this.dateIn = dateIn;
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDate dateOut) {
        this.dateOut = dateOut;
    }

    public int getExpositionId() {
        return expositionId;
    }

    public void setExpositionId(int expositionId) {
        this.expositionId = expositionId;
    }

    public String getExpositionTheme() {
        return expositionTheme;
    }

    public void setExpositionTheme(String expositionTheme) {
        this.expositionTheme = expositionTheme;
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
        return "BoughtTicket{" +
                "dateIn=" + dateIn +
                ", dateOut=" + dateOut +
                ", expositionId=" + expositionId +
                ", expositionTheme='" + expositionTheme + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", cost=" + cost +
                '}';
    }
}
