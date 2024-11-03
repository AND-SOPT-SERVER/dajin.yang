package org.sopt.diary.api;

import org.sopt.diary.model.Category;
import org.sopt.diary.dto.*;
import org.sopt.diary.service.DiaryService;
import org.sopt.diary.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;
    private final UserService userService;

    public DiaryController(DiaryService diaryService, UserService userService) {
        this.diaryService = diaryService;
        this.userService = userService;
    }

    @PostMapping("/diaries")
    ResponseEntity<DiaryResponse> createDiary(
            @RequestHeader("userId") Long userId,
            @RequestBody DiaryCreateRequest diaryCreateRequest) {
        DiaryResponse diaryResponse = diaryService.createDiary(userId, diaryCreateRequest);
        return ResponseEntity.ok(diaryResponse);
    }

    @GetMapping("/diaries/list")
    public ResponseEntity<Page<DiaryDirectoryResponse>> getDiaryList(
            @RequestHeader("userId") Long userId,
            @RequestParam(value = "category", required = false) Category category,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<DiaryDirectoryResponse> diaryDirectoryResponses;

        if (category != null) {
            if ("bodyLength".equals(orderBy)) {
                diaryDirectoryResponses = diaryService.getDiaryByCategoryLength(userId, category, pageRequest);
            } else {
                diaryDirectoryResponses = diaryService.getDiaryByCategory(userId, category, pageRequest);
            }
        } else {
            if ("bodyLength".equals(orderBy)) {
                diaryDirectoryResponses = diaryService.getSortDiary(userId, pageRequest);
            } else {
                diaryDirectoryResponses = diaryService.getDiaryList(userId, pageRequest);
            }
        }
        return ResponseEntity.ok(diaryDirectoryResponses);
    }

    @GetMapping("/diaries/{id}")
    public ResponseEntity<DiaryDetailResponse> getDiaryDetail(
            @RequestHeader("userId") Long userId,
            @PathVariable long id) {
        DiaryDetailResponse diaryDetailResponse = diaryService.getDiaryDetail(userId, id);
        return ResponseEntity.ok(diaryDetailResponse);
    }

    @PatchMapping("/diaries/{id}")
    public ResponseEntity<DiaryResponse> updateDiaryBody(
            @RequestHeader("userId") Long userId,
            @PathVariable Long id,
            @RequestBody DiaryUpdateRequest diaryUpdateRequest) {
        DiaryResponse diaryResponse = diaryService.updateDiary(userId, id, diaryUpdateRequest);
        return ResponseEntity.ok(diaryResponse);
    }

    @DeleteMapping("/diaries/{id}")
    public ResponseEntity<DiaryResponse> deleteDiary(
            @RequestHeader("userId") Long userId,
            @PathVariable Long id) {
        DiaryResponse diaryResponse = diaryService.deleteDiary(userId, id);
        return ResponseEntity.ok(diaryResponse);
    }

    @GetMapping("/diaries/home")
    public ResponseEntity<List<DiaryDirectoryResponse>> getMainHome(
            @RequestHeader("userId") Long userId) {
        List<DiaryDirectoryResponse> diaryDirectoryResponses = diaryService.getMainHome(userId);
        return ResponseEntity.ok(diaryDirectoryResponses);
    }

    @GetMapping("/diaries/my")
    public ResponseEntity<List<DiaryDirectoryResponse>> getMyDiaries(
            @RequestHeader("userId") Long userId) {
        List<DiaryDirectoryResponse> diaryDirectoryResponses = diaryService.getMyDiaries(userId);
        return ResponseEntity.ok(diaryDirectoryResponses);
    }

    @PatchMapping("/diaries/{id}/publish")
    public ResponseEntity<DiaryResponse> publishDiary(
            @PathVariable Long id,
            @RequestParam Boolean isPublished) {
        DiaryResponse diaryResponse = diaryService.publishDiary(id, isPublished);
        return ResponseEntity.ok(diaryResponse);
    }
}