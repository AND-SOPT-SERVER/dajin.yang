package org.sopt.diary.repository;

import org.sopt.diary.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    List<DiaryEntity> findTop10ByOrderByCreatedAtDesc();

    @Query("SELECT d.createdAt FROM DiaryEntity d ORDER BY d.createdAt DESC")
    Optional<LocalDateTime> findTopByOrderByCreatedAtDesc();

    @Query("SELECT d FROM DiaryEntity d ORDER BY LENGTH(d.body) DESC")
    List<DiaryEntity> findTop10ByOrderByBodyLengthDesc();

    boolean existsByTitle(String title);

    List<DiaryEntity> findByCategory(Category category);

    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category ORDER BY LENGTH(d.body) DESC")
    List<DiaryEntity> findByCategoryOrderByBodyLengthDesc(Category category);
}