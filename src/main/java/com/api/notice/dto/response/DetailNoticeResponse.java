package com.api.notice.dto.response;

import com.api.notice.domain.Notice;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DetailNoticeResponse(
        long noticeId,
        String title,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createDate,
        long readCount,
        String createUser
) {
    public static DetailNoticeResponse from(Notice notice) {
        return new DetailNoticeResponse(
                notice.getNoticeId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCreateDate(),
                notice.getReadCount(),
                notice.getCreateUser()
        );
    }
}
