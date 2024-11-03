package org.sopt.diary.repository;

import jakarta.persistence.*;
import org.sopt.diary.model.Category;
import java.time.LocalDateTime;

@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Column
    private String body;

    @Column
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    private boolean isPublished = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;  // UserEntity로 변경하여 타입 일치

    public DiaryEntity() {
    }

    public DiaryEntity(final String title, final String body, final Category category, final UserEntity user) {
        this.title = title;
        this.body = body;
        this.category = category;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreateAt() {
        return createdAt;
    }

    public UserEntity getUser() {
        return user;
    }

    public void updateBody(String body) {
        this.body = body;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }
}
