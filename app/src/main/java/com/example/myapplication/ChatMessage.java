package com.example.myapplication;

public class ChatMessage {
    private String sender;
    private String message;
    private String time;

    public ChatMessage(String sender, String message, String time) {
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public boolean isMine() {
        return sender.equals(CurrentUser.getNickname());
    }
}
