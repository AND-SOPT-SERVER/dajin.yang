package org.sopt.diary.dto;

public class DiaryDirectoryResponse {
    private long id;
    private String title;

    public DiaryDirectoryResponse(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
