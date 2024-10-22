package org.sopt.diary.dto;

public class DiaryUpdateRequest {
    private String body;

    public DiaryUpdateRequest() {
    }

    public DiaryUpdateRequest(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
