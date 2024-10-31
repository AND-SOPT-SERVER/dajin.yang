package org.sopt.diary.dto;

import java.time.LocalDateTime;

public class DiaryResponse {
    private long id;
    private String title;

    private String body;

    public DiaryResponse(long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
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
}
