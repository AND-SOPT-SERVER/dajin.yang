package org.sopt.seminar1;

import org.sopt.seminar1.DiaryRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();
    private final Map<Long, Map<LocalDate, Integer>> count = new HashMap<>();


    // 일기 작성
    void writeDiary(final String body) {

        checkLength(body);

        final Diary diary = new Diary(null, body);
        diaryRepository.save(diary);

        count.put(diary.getId(), new HashMap<>());
    }

    // 일기 조회
    List<Diary> getDiaryList() {
        return diaryRepository.findAll();
    }

    // 일기 수정
    void patchDiary(final String id, final String body) {
        Long longId = Converter.parseId(id);
        checkLength(body);

        LocalDate today = LocalDate.now();
        Map<LocalDate, Integer> diaryCnt = count.getOrDefault(longId, new HashMap<>());
        int todayCnt = diaryCnt.getOrDefault(today, 0);

        if (todayCnt >= 2) {
            throw new IllegalStateException("일기 수정을 하루에 2번만 가능합니다");
        }

        diaryRepository.patch(longId, body);
        diaryCnt.put(today, todayCnt + 1);
        count.put(longId, diaryCnt);
    }

    // 일기 삭제
    void deleteDiary(final String id) {
        Long longId = Converter.parseId(id);
        diaryRepository.delete(longId);
    }

    // 일기 복구
    void restoreDiary(final String id) {
        Long longId = Converter.parseId(id);
        diaryRepository.restore(longId);
    }

    private void checkLength(final String body) {
        if (body.length() > 30) {
            throw new IllegalArgumentException("30글자 초과");
        }
    }
}
