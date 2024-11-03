package org.sopt.diary.dto;

public class SignUpRequest {
    private String username;
    private String password;
    private String nickname;

    public SignUpRequest(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
