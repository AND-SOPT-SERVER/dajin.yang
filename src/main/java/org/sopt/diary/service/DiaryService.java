package org.sopt.diary.service;

import org.sopt.diary.dto.*;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public DiaryResponse createDiary(DiaryCreateRequest diaryCreateRequest) {
        DiaryEntity diaryEntity = new DiaryEntity(diaryCreateRequest.getTitle(), diaryCreateRequest.getBody());
        DiaryEntity saveDiary = diaryRepository.save(diaryEntity);

        return new DiaryResponse(saveDiary.getId(), saveDiary.getTitle(), saveDiary.getBody());
    }

    public List<DiaryDirectoryResponse> getDiaryList() {
        List<DiaryEntity> diaryEntities = diaryRepository.findTop10ByOrderByCreatedAtDesc();

        return diaryEntities.stream()
                .map(diaryEntity -> new DiaryDirectoryResponse(diaryEntity.getId(), diaryEntity.getTitle()))
                .collect(Collectors.toList());
    }

    public DiaryDetailResponse getDiaryDetail(long id) {
        DiaryEntity diaryEntity = findDiaryByIdOrThrow(id);

        return new DiaryDetailResponse(
                diaryEntity.getId(),
                diaryEntity.getTitle(),
                diaryEntity.getBody(),
                diaryEntity.getCreateAt()
        );
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

    public boolean isValid(String body) {
        return body.length() <= 30;
    }

    private DiaryEntity findDiaryByIdOrThrow(Long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID"));
    }

    public List<Diary> getList() {
        List<DiaryEntity> diaryEntitiyList = diaryRepository.findAll();
        final List<Diary> diaryList = new ArrayList<>();

        for (DiaryEntity diaryEntity : diaryEntitiyList) {
            diaryList.add(
                    new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody())
            );
        }

        return diaryList;
    }
}
