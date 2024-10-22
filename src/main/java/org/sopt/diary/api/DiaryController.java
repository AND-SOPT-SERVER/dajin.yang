package org.sopt.diary.api;

import org.sopt.diary.dto.*;
import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/diaries")
    ResponseEntity<DiaryResponse> createDiary(@RequestBody DiaryCreateRequest diaryCreateRequest) {
        if (!diaryService.isValid(diaryCreateRequest.getBody())) {
            throw new IllegalArgumentException("30글자 초과");
        }

        DiaryResponse diaryResponse = diaryService.createDiary(diaryCreateRequest);
        return ResponseEntity.ok(diaryResponse);
    }

    @GetMapping("/diaries/list")
    public ResponseEntity<List<DiaryDirectoryResponse>> getDiaryList() {
        List<DiaryDirectoryResponse> diaryDirectoryResponses = diaryService.getDiaryList();
        return ResponseEntity.ok(diaryDirectoryResponses);
    }

    @GetMapping("/diaries/{id}")
    public ResponseEntity<DiaryDetailResponse> getDiaryDetail(@PathVariable long id) {
        DiaryDetailResponse diaryDetailResponse = diaryService.getDiaryDetail(id);
        return ResponseEntity.ok(diaryDetailResponse);
    }

    @PatchMapping("/diaries/{id}")
    public ResponseEntity<DiaryResponse> updateDiaryBody(
            @PathVariable Long id,
            @RequestBody DiaryUpdateRequest diaryUpdateRequest) {

        if (!diaryService.isValid(diaryUpdateRequest.getBody())) {
            throw new IllegalArgumentException("30글자 초과");
        }

        DiaryResponse diaryResponse = diaryService.updateDiary(id, diaryUpdateRequest);
        return ResponseEntity.ok(diaryResponse);
    }

    @DeleteMapping("/diaries/{id}")
    public ResponseEntity<DiaryResponse> deleteDiary(@PathVariable Long id) {
        DiaryResponse diaryResponse = diaryService.deleteDiary(id);
        return ResponseEntity.ok(diaryResponse);
    }

    @GetMapping("/post")
    ResponseEntity<DiaryListResponse> get() {
        List<Diary> diaryList = diaryService.getList();

        List<DiaryResponse> diaryResponsesList = new ArrayList<>();

        for (Diary diary : diaryList) {
            diaryResponsesList.add(new DiaryResponse(diary.getId(), diary.getTitle(), diary.getBody()));
        }
        return ResponseEntity.ok(new DiaryListResponse(diaryResponsesList));
    }
}
