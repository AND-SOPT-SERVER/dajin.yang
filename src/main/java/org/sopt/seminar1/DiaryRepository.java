package org.sopt.seminar1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {
    private final Map<Long, Diary> storage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();

    void save(final Diary diary) {
        final long id = numbering.addAndGet(1);
        diary.setId(id);

        storage.put(id, diary);
    }

    List<Diary> findAll() {
        // diary를 담을 자료구조
        final List<Diary> diaryList = new ArrayList<>();

        for (Diary diary : storage.values()) {
            if (!diary.isDeleted()) {
                diaryList.add(diary);
            }
        }

        // 불러온 자료구조를 응답
        return diaryList;
    }

    void patch(final Long id, final String body) {
        Diary diary = storage.get(id);
        if (diary != null && !diary.isDeleted()) {
            diary.setBody(body);
        }
    }

    void delete(final Long id) {
        Diary diary = storage.get(id);
        if (diary != null && !diary.isDeleted()) {
            diary.setDeleted(true);
        }
    }

    void restore(final Long id) {
        Diary diary = storage.get(id);
        if (diary != null && diary.isDeleted()) {
            diary.setDeleted(false);
        }
    }
}