package com.grpetr.task.db.entity;

public class Hall {
    private int id;
    private String hallName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "id=" + id +
                ", hallName=" + hallName +
                '}';
    }
}
