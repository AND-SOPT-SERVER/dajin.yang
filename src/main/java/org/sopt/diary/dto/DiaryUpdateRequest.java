package org.sopt.diary.dto;

public class DiaryUpdateRequest {
    private String body;
    private boolean isPublished;

    public DiaryUpdateRequest() {
    }

    public DiaryUpdateRequest(String body, boolean isPublished) {
        this.isPublished = isPublished;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isPublished() {
        return isPublished;
    }
}
