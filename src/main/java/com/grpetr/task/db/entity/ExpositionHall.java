package com.grpetr.task.db.entity;

/**
 * Simple Entity for ExpositionHall
 */
public class ExpositionHall {
    private int id;
    private int expositionId;
    private int hallId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExpositionId() {
        return expositionId;
    }

    public void setExpositionId(int expositionId) {
        this.expositionId = expositionId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    @Override
    public String toString() {
        return "ExpositionHall{" +
                "id=" + id +
                ", expositionId=" + expositionId +
                ", hallId=" + hallId +
                '}';
    }
}
