package com.grpetr.task.db.constant;

import com.grpetr.task.db.entity.User;

public enum Language {
    ru, en;
    public static Language getLanguage(User user){
        Language language = user.getLanguage();
        return language;
    }
    public String getName() {
        return name().toLowerCase();
    }
}
