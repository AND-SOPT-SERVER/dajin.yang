package org.sopt.seminar1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {
    private final Map<Long, String> storage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();

    void save(final Diary diary) {
        final long id = numbering.addAndGet(1);

        storage.put(id, diary.getBody());
    }

    List<Diary> findAll() {
        // diary를 담을 자료구조
        final List<Diary> diaryList = new ArrayList<>();

        for (Map.Entry<Long, String> entry : storage.entrySet()) {
            diaryList.add(new Diary(entry.getKey(), entry.getValue()));
        }

        // 불러온 자료구조를 응답
        return diaryList;
    }

    void patch(final Long id, final String body) {
        storage.replace(id, body);
    }

    void delete(final Long id) {

        storage.remove(id);
    }
}