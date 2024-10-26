package org.sopt.diary.validator;

import org.sopt.diary.dto.DiaryCreateRequest;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

@Component
public class DiaryValidator {
    private final DiaryRepository diaryRepository;

    public DiaryValidator(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void validate(DiaryCreateRequest request) {
        if (diaryRepository.existsByTitle(request.getTitle())) {
            throw new IllegalArgumentException("이미 존재하는 제목");
        }

        if (request.getBody().length() > 30) {
            throw new IllegalArgumentException("30글자 초과");
        }
    }
}