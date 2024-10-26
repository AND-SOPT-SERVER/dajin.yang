package org.sopt.diary.dto;

import org.sopt.diary.model.Category;

public class DiaryCreateRequest {
    private String title;
    private String body;
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}