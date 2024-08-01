package com.api.notice.controller;

import com.api.notice.dto.request.SaveNoticeRequest;
import com.api.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/{noticeId}")
    public ResponseEntity<?> getDetailNotice(@PathVariable("noticeId") Long noticeId) {
        return ResponseEntity.ok(noticeService.getDetailNotice(noticeId));
    }

    @PostMapping
    public ResponseEntity<?> saveNotice(@RequestBody @Valid SaveNoticeRequest saveNoticeRequest) {
        return ResponseEntity.ok(noticeService.saveNotice(saveNoticeRequest));
    }

    @PatchMapping("/{noticeId}")
    public ResponseEntity<?> updateNotice(@PathVariable("noticeId") Long noticeId,
                                          @RequestBody @Valid SaveNoticeRequest saveNoticeRequest) {
        return ResponseEntity.ok(noticeService.updateNotice(noticeId, saveNoticeRequest));
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<?> deleteNotice(@PathVariable("noticeId") Long noticeId) {
        return ResponseEntity.ok(noticeService.deleteNotice(noticeId));
    }

}
