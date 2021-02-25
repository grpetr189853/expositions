package com.grpetr.task.db.constant;

import com.grpetr.task.db.entity.User;

/**
 * Enum with possible user's access levels
 */
public enum AccessLevel {
    USER, ADMIN;

    public static AccessLevel getAccessLevel(User user) {
        AccessLevel accessLevel = user.getAccessLevel();
        return accessLevel;
    }

    public String getName() {
        return name().toLowerCase();
    }
}
