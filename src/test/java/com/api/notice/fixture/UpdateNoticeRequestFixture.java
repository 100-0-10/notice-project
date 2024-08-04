package com.api.notice.fixture;

import com.api.notice.domain.Notice;
import com.api.notice.dto.request.UpdateNoticeRequest;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateNoticeRequestFixture {

    public static UpdateNoticeRequest create(String title,
                                             String content,
                                             String startDateTIme,
                                             String endDateTime,
                                             List<Long> deleteFileIds) {
        return new UpdateNoticeRequest(title, content, startDateTIme, endDateTime, deleteFileIds);
    }

    public static Notice create(Long id,
                                String title,
                                String content,
                                LocalDateTime startDateTIme,
                                LocalDateTime endDateTime) {
        return new Notice(id, title, content, startDateTIme, endDateTime, 0L, "Y", null, null);
    }

}
