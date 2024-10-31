package org.sopt.diary.service;

import org.sopt.diary.model.Category;
import org.sopt.diary.dto.*;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.sopt.diary.validator.DiaryValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DiaryTimeService diaryTimeService;
    private final DiaryValidator diaryValidator;

    public DiaryService(DiaryRepository diaryRepository, DiaryTimeService diaryTimeService, DiaryValidator diaryValidator) {
        this.diaryRepository = diaryRepository;
        this.diaryTimeService = diaryTimeService;
        this.diaryValidator = diaryValidator;
    }

    public DiaryResponse createDiary(DiaryCreateRequest diaryCreateRequest) {
        if (!diaryTimeService.fiveTimeCheck()) {
            throw new IllegalArgumentException("아직 5분 안됐어요");
        }

        diaryValidator.validate(diaryCreateRequest);

        DiaryEntity diaryEntity = new DiaryEntity(diaryCreateRequest.getTitle(), diaryCreateRequest.getBody(), diaryCreateRequest.getCategory());
        DiaryEntity saveDiary = diaryRepository.save(diaryEntity);

        return new DiaryResponse(saveDiary.getId(), saveDiary.getTitle(), saveDiary.getBody());
    }

    public List<DiaryDirectoryResponse> getDiaryList() {
        List<DiaryEntity> diaryEntities = diaryRepository.findTop10ByOrderByCreatedAtDesc();

        return mapToDiaryResponse(diaryEntities);
    }

    public List<DiaryDirectoryResponse> getDiaryByCategoryLength(Category category) {
        List<DiaryEntity> diaryEntities = diaryRepository.findByCategoryOrderByBodyLengthDesc(category);
        return mapToDiaryResponse(diaryEntities);
    }

    public DiaryDetailResponse getDiaryDetail(long id) {
        DiaryEntity diaryEntity = findDiaryByIdOrThrow(id);

        return new DiaryDetailResponse(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.getCreateAt());
    }

    public DiaryResponse updateDiary(long id, DiaryUpdateRequest diaryUpdateRequest) {
        DiaryEntity existingDiary = findDiaryByIdOrThrow(id);

        // 본문만 업데이트
        existingDiary.body = diaryUpdateRequest.getBody();

        diaryRepository.save(existingDiary);

        return new DiaryResponse(existingDiary.getId(), existingDiary.getTitle(), existingDiary.getBody());
    }

    public DiaryResponse deleteDiary(Long id) {
        DiaryEntity diaryEntity = findDiaryByIdOrThrow(id);

        diaryRepository.deleteById(id);

        return new DiaryResponse(id, diaryEntity.getTitle(), diaryEntity.getBody());
    }

    public List<DiaryDirectoryResponse> getSortDiary() {
        List<DiaryEntity> diaryEntities = diaryRepository.findTop10ByOrderByBodyLengthDesc();

        return mapToDiaryResponse(diaryEntities);
    }

    public List<DiaryDirectoryResponse> getDiaryByCategory(Category category) {
        List<DiaryEntity> diaryEntities = diaryRepository.findByCategory(category);
        return mapToDiaryResponse(diaryEntities);
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