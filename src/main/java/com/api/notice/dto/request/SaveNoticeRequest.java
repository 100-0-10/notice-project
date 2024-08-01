package com.api.notice.dto.request;

import com.api.notice.domain.Notice;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record SaveNoticeRequest(
        @NotBlank(message = "제목")
        String title,
        @NotBlank(message = "내용")
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime endDate,
        MultipartFile[] files
) {
    public Notice toEntity() {
        return Notice.create().saveNoticeRequest(this).build();
    }
}
