package com.api.notice.domain;

import com.api.notice.dto.request.SaveNoticeRequest;
import com.api.notice.util.audit.BaseUserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseUserEntity {

    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Getter
    @Column(name = "title")
    public String title;

    @Getter
    @Column(name = "content")
    private String content;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Getter
    @Column(name = "read_count")
    private Long readCount;

    @Builder(builderMethodName = "create")
    private Notice(SaveNoticeRequest saveNoticeRequest) {
        this.title = saveNoticeRequest.title();
        this.content = saveNoticeRequest.content();
        this.startDateTime = saveNoticeRequest.startDate();
        this.endDateTime = saveNoticeRequest.endDate();
        this.readCount = 0L;
    }

    public void update(String title, String content, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.content = content;
        this.startDateTime = startDate;
        this.endDateTime = endDate;
    }

}
