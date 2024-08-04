package com.api.notice.fixture;

import com.api.notice.domain.Notice;
import com.api.notice.dto.request.CreateNoticeRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class NoticeFixture {

    public static Notice create(CreateNoticeRequest createNoticeRequest) {
        return createNoticeRequest.toNoticeEntity();
    }

    public static Notice create(String title,
                                String content,
                                String startDateTIme,
                                String endDateTime) {
        CreateNoticeRequest createNoticeRequest =
                new CreateNoticeRequest(title, content, startDateTIme, endDateTime);
        return createNoticeRequest.toNoticeEntity();
    }

    public static Notice create(Long id,
                                String title,
                                String content,
                                LocalDateTime startDateTIme,
                                LocalDateTime endDateTime) {
        return new Notice(id, title, content, startDateTIme, endDateTime, 0L, "Y", null, new ArrayList<>());
    }

}
