package org.sopt.diary.service;

import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class DiaryTimeService {
    private final DiaryRepository diaryRepository;

    public DiaryTimeService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public boolean fiveTimeCheck() {
/*        Optional<LocalDateTime> lastDiary = diaryRepository.findTopByOrderByCreatedAtDesc();

        if (lastDiary.isEmpty()) {
            return true;
        }

        LocalDateTime lastCreateAt = lastDiary.get();
        LocalDateTime current = LocalDateTime.now();

        return ChronoUnit.MINUTES.between(lastCreateAt, current) >= 5;*/

        return true;
    }
}
