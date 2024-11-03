package org.sopt.diary.dto;

public class UserResponse {
    private Long userId;
    private String username;
    private String nickname;

    public UserResponse(Long userId, String username, String nickname) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getUserId() {
        return userId;
    }
}
