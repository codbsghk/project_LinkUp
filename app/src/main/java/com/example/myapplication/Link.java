package com.example.myapplication;

public class Link {
    private String name;           // 모임명
    private String description;    // 모임 소개
    private int type;              // 모임 타입 (0: 여러번, 1: 한 번)
    private int joinPassword;      // 가입 조건 (-1: 공개모임, else: 비밀번호)

    public Link(String name, String description, int type, int joinPassword) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.joinPassword = joinPassword;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public int getJoinPassword() {
        return joinPassword;
    }

    public boolean isPublic() {
        return joinPassword == -1;
    }
}

