package com.api.notice.dto.response;

import com.api.notice.domain.Notice;

import java.time.LocalDateTime;

public record DetailNoticeResponse(
        String title,
        String content,
        LocalDateTime createDate,
        long readCount,
        String createUser
) {
    public static DetailNoticeResponse from(Notice notice) {
        return new DetailNoticeResponse(
                notice.getTitle(),
                notice.getContent(),
                notice.getCreateDate(),
                notice.getReadCount(),
                notice.getCreateUser()
        );
    }
}
