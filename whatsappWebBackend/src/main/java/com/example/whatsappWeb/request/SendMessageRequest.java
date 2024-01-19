package com.example.whatsappWeb.request;

public class SendMessageRequest {
    private Long userId;
    private Long chatId;
    private String content;

    @Override
    public String toString() {
        return "SendMessageRequest{" +
                "userId=" + userId +
                ", chatId=" + chatId +
                ", content='" + content + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SendMessageRequest(Long userId, Long chatId, String content) {
        this.userId = userId;
        this.chatId = chatId;
        this.content = content;
    }

    public SendMessageRequest() {
    }
}
