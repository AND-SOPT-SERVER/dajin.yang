package org.sopt.diary.repository;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.sopt.diary.Category;

import java.time.LocalDateTime;

@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true)
    public String title;

    @Column
    public String body;

    @Column
    public LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    public Category category;

    public DiaryEntity() {

    }

    public DiaryEntity(final String title, final String body, final Category category) {
        this.title = title;
        this.body = body;
        this.category = category;
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
}
