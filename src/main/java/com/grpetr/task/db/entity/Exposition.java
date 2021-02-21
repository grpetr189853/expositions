package com.grpetr.task.db.entity;

import java.time.LocalDate;

public class Exposition {
    private int id;
    private String theme;
    private int ticketPrice;
    private LocalDate dateIn;
    private LocalDate dateOut;
    private String hallsNames;
    private int ticketsCount;

    private String imgName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

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

    public String getHallsNames() {
        return hallsNames;
    }

    public void setHallsNames(String hallsNames) {
        this.hallsNames = hallsNames;
    }

    public int getTicketsCount() {
        return ticketsCount;
    }

    public void setTicketsCount(int ticketsCount) {
        this.ticketsCount = ticketsCount;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    @Override
    public String toString() {
        return "Exposition{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", dateIn=" + dateIn +
                ", dateOut=" + dateOut +
                ", hallsNames='" + hallsNames + '\'' +
                ", ticketsCount=" + ticketsCount +
                ", imgName='" + imgName + '\'' +
                '}';
    }
}
