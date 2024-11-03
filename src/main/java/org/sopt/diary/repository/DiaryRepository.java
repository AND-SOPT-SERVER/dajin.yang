package org.sopt.diary.repository;

import org.sopt.diary.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    List<DiaryEntity> findTop10ByOrderByCreatedAtDesc();

    Page<DiaryEntity> findTop10ByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT d.createdAt FROM DiaryEntity d ORDER BY d.createdAt DESC")
    Optional<LocalDateTime> findTopByOrderByCreatedAtDesc();

    @Query("SELECT d FROM DiaryEntity d ORDER BY LENGTH(d.body) DESC")
    List<DiaryEntity> findTop10ByOrderByBodyLengthDesc();

    @Query("SELECT d FROM DiaryEntity d ORDER BY LENGTH(d.body) DESC")
    Page<DiaryEntity> findTop10ByOrderByBodyLengthDesc(Pageable pageable);

    boolean existsByTitle(String title);

    List<DiaryEntity> findByCategory(Category category);

    Page<DiaryEntity> findByCategory(Category category, Pageable pageable);

    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category ORDER BY LENGTH(d.body) DESC")
    List<DiaryEntity> findByCategoryOrderByBodyLengthDesc(Category category);

    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category ORDER BY LENGTH(d.body) DESC")
    Page<DiaryEntity> findByCategoryOrderByBodyLengthDesc(Category category, Pageable pageable);

    @Query("SELECT d FROM DiaryEntity d WHERE d.user.id = :userId ORDER BY d.createdAt DESC")
    List<DiaryEntity> findMainHomeByUserId(Long userId);

    @Query("SELECT d FROM DiaryEntity d WHERE d.user.id = :userId ORDER BY d.createdAt DESC")
    Page<DiaryEntity> findMainHomeByUserId(Long userId, Pageable pageable);

    List<DiaryEntity> findByUserId(Long userId);

}