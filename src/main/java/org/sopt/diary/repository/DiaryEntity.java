package org.sopt.diary.repository;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String title;

    @Column
    public String body;

    @Column
    public LocalDateTime createdAt;

    public DiaryEntity() {

    }

    public DiaryEntity(final String title, final String body) {
        this.title = title;
        this.body = body;
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
