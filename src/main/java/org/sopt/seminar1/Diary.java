package org.sopt.seminar1;

public class Diary {
    private Long id;
    private String body;
    private boolean isDeleted;
    public Diary(Long id, String body) {
        this.id = id;
        this.body = body;
        this.isDeleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
