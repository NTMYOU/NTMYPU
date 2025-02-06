package com.example.ntmyou.Config;

public enum Role {
    USER("일반회원"),
    MASTER("운영자");
    private final String description;
    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return  description;
    }

}
