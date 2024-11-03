package org.sopt.diary.model;

public class Diary {
    private final long id;
    private final String title;

    private final String body;

    public Diary(long id, String title, String body) {
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
