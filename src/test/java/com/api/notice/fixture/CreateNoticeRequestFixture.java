package com.api.notice.fixture;

import com.api.notice.domain.Notice;
import com.api.notice.dto.request.CreateNoticeRequest;

import java.time.LocalDateTime;

public class CreateNoticeRequestFixture {

    public static CreateNoticeRequest create(String title,
                                String content,
                                String startDateTIme,
                                String endDateTime) {
        return new CreateNoticeRequest(title, content, startDateTIme, endDateTime);
    }

    public static Notice create(Long id,
                                String title,
                                String content,
                                LocalDateTime startDateTIme,
                                LocalDateTime endDateTime) {
        return new Notice(id, title, content, startDateTIme, endDateTime, 0L, "Y", null, null);
    }

}
