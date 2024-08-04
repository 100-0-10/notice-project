package com.api.notice.controller;

import com.api.notice.dto.request.CreateNoticeRequest;
import com.api.notice.dto.request.MultipartFilesRequest;
import com.api.notice.dto.request.UpdateNoticeRequest;
import com.api.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<?> getDetails(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(noticeService.getNotices(pageable));
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<?> getDetailNotice(@PathVariable("noticeId") Long noticeId) {
        return ResponseEntity.ok(noticeService.getDetailNotice(noticeId));
    }

    @PostMapping
    public ResponseEntity<?> saveNotice(@Valid CreateNoticeRequest createNoticeRequest,
                                        MultipartFilesRequest files) {
        return ResponseEntity.ok(noticeService.saveNotice(createNoticeRequest, files));
    }

    @PatchMapping("/{noticeId}")
    public ResponseEntity<?> updateNotice(@PathVariable("noticeId") Long noticeId,
                                          @Valid UpdateNoticeRequest updateNoticeRequest,
                                          MultipartFilesRequest files) {
        return ResponseEntity.ok(noticeService.updateNotice(noticeId, updateNoticeRequest, files));
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<?> deleteNotice(@PathVariable("noticeId") Long noticeId) {
        return ResponseEntity.ok(noticeService.deleteNotice(noticeId));
    }

}
