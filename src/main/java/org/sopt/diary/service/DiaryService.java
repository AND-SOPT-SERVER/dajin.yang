package org.sopt.diary.service;

import org.sopt.diary.model.Category;
import org.sopt.diary.dto.*;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.sopt.diary.repository.UserEntity;
import org.sopt.diary.repository.UserRepository;
import org.sopt.diary.validator.DiaryValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DiaryTimeService diaryTimeService;
    private final DiaryValidator diaryValidator;
    private final UserRepository userRepository;

    public DiaryService(DiaryRepository diaryRepository, DiaryTimeService diaryTimeService, DiaryValidator diaryValidator, UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.diaryTimeService = diaryTimeService;
        this.diaryValidator = diaryValidator;
        this.userRepository = userRepository;
    }

    public DiaryResponse createDiary(Long userId, DiaryCreateRequest diaryCreateRequest) {
        if (!diaryTimeService.fiveTimeCheck()) {
            throw new IllegalArgumentException("아직 5분 안됐어요");
        }

        diaryValidator.validate(diaryCreateRequest);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));

        DiaryEntity diaryEntity = new DiaryEntity(diaryCreateRequest.getTitle(), diaryCreateRequest.getBody(), diaryCreateRequest.getCategory(), user);
        DiaryEntity savedDiary = diaryRepository.save(diaryEntity);

        return new DiaryResponse(savedDiary.getId(), savedDiary.getTitle(), savedDiary.getBody(), savedDiary.isPublished());
    }

    public Page<DiaryDirectoryResponse> getDiaryList(Long userId, Pageable pageable) {
        return diaryRepository.findTop10ByOrderByCreatedAtDesc(pageable)
                .map(diaryEntity -> new DiaryDirectoryResponse(diaryEntity.getId(), diaryEntity.getTitle()));
    }

    public Page<DiaryDirectoryResponse> getDiaryByCategoryLength(Long userId, Category category, Pageable pageable) {
        return diaryRepository.findByCategoryOrderByBodyLengthDesc(category, pageable)
                .map(diaryEntity -> new DiaryDirectoryResponse(diaryEntity.getId(), diaryEntity.getTitle()));
    }

    public DiaryDetailResponse getDiaryDetail(Long userId, long id) {
        DiaryEntity diaryEntity = findDiaryByIdOrThrow(id);
        return new DiaryDetailResponse(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.getCreateAt());
    }

    public DiaryResponse updateDiary(Long userId, Long id, DiaryUpdateRequest diaryUpdateRequest) {
        DiaryEntity existingDiary = findDiaryByIdOrThrow(id);
        if (!existingDiary.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다");
        }
        existingDiary.updateBody(diaryUpdateRequest.getBody());

        diaryRepository.save(existingDiary);
        return new DiaryResponse(existingDiary.getId(), existingDiary.getTitle(), existingDiary.getBody(), existingDiary.isPublished());
    }

    public DiaryResponse deleteDiary(Long userId, Long id) {
        DiaryEntity diaryEntity = findDiaryByIdOrThrow(id);
        if (!diaryEntity.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다");
        }
        diaryRepository.deleteById(id);
        return new DiaryResponse(id, diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.isPublished());
    }

    public Page<DiaryDirectoryResponse> getSortDiary(Long userId, Pageable pageable) {
        return diaryRepository.findTop10ByOrderByBodyLengthDesc(pageable)
                .map(diaryEntity -> new DiaryDirectoryResponse(diaryEntity.getId(), diaryEntity.getTitle()));
    }

    public Page<DiaryDirectoryResponse> getDiaryByCategory(Long userId, Category category, Pageable pageable) {
        return diaryRepository.findByCategory(category, pageable)
                .map(diaryEntity -> new DiaryDirectoryResponse(diaryEntity.getId(), diaryEntity.getTitle()));
    }

    public List<DiaryDirectoryResponse> getMainHome(Long userId) {
        List<DiaryEntity> diaries = diaryRepository.findMainHomeByUserId(userId);
        return mapToDiaryResponse(diaries);
    }

    public List<DiaryDirectoryResponse> getMyDiaries(Long userId) {
        List<DiaryEntity> diaries = diaryRepository.findByUserId(userId);
        return mapToDiaryResponse(diaries);
    }

    public DiaryResponse publishDiary(Long id, boolean isPublished) {
        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다이어리 ID"));
        diaryEntity.setPublished(isPublished);
        DiaryEntity saveDiary = diaryRepository.save(diaryEntity);

        return new DiaryResponse(saveDiary.getId(), saveDiary.getTitle(), saveDiary.getBody(), saveDiary.isPublished());
    }

    private DiaryEntity findDiaryByIdOrThrow(Long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID"));
    }

    private List<DiaryDirectoryResponse> mapToDiaryResponse(List<DiaryEntity> diaryEntities) {
        return diaryEntities.stream()
                .map(diaryEntity -> new DiaryDirectoryResponse(diaryEntity.getId(), diaryEntity.getTitle()))
                .collect(Collectors.toList());
    }
}
