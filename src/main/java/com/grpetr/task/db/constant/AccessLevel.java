package com.grpetr.task.db.constant;

import com.grpetr.task.db.entity.User;

public enum AccessLevel {
    USER,ADMIN;

    public static AccessLevel getAccessLevel(User user) {
        AccessLevel accessLevel = user.getAccessLevel();
        return accessLevel;
    }

    public String getName() {
        return name().toLowerCase();
    }
}
