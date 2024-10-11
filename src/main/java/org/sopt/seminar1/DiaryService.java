package org.sopt.seminar1;

import org.sopt.seminar1.DiaryRepository;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    // 일기 작성
    void writeDiary(final String body) {
        checkLength(body);

        final Diary diary = new Diary(null, body);
        diaryRepository.save(diary);
    }

    // 일기 조회
    List<Diary> getDiaryList() {
        return diaryRepository.findAll();
    }

    // 일기 수정
    void patchDiary(final String id, final String body) {
        Long longId = Converter.parseId(id);

        checkLength(body);

        diaryRepository.patch(longId, body);
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
