package org.sopt.diary.dto;

public class DiaryCreateRequest {
    private String title;
    private String body;

    public DiaryCreateRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
