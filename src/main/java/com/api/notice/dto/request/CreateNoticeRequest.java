package com.api.notice.dto.request;

import com.api.notice.domain.Notice;
import com.api.notice.util.validation.NotDateTime;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record CreateNoticeRequest(
        @NotBlank(message = "제목")
        String title,
        @NotBlank(message = "내용")
        String content,
        @NotDateTime(message = "공지시작일시", pattern = PATTERN)
        String startDateTime,
        @NotDateTime(message = "공지종료일시", pattern = PATTERN)
        String endDateTime
) {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    public Notice toNoticeEntity() {
        return Notice.create().createNoticeRequest(this).build();
    }

    public LocalDateTime getStartDateTime() {
        return LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern(PATTERN));
    }

    public LocalDateTime getEndDateTime() {
        return LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern(PATTERN));
    }
}
