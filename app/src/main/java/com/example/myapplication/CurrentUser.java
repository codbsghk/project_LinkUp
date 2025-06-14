package com.example.myapplication;

public class CurrentUser {
    private static String nickname = "나"; // 기본값은 "나"

    public static void setNickname(String name) {
        nickname = name;
    }

    public static String getNickname() {
        return nickname;
    }
}
