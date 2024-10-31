package org.sopt.diary.dto;

import java.time.LocalDateTime;

public class DiaryDetailResponse {
    private long id;
    private String title;
    private String body;
    private LocalDateTime createdAt;

    public DiaryDetailResponse(long id, String title, String body, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
