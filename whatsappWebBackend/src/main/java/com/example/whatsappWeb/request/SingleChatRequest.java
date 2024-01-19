package com.example.whatsappWeb.request;

public class SingleChatRequest {
    private Long userId;

    @Override
    public String toString() {
        return "SingleChatRequest{" +
                "userId=" + userId +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public SingleChatRequest() {
    }

    public SingleChatRequest(Long userId) {
        this.userId = userId;
    }
}
