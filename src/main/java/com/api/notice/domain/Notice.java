package com.api.notice.domain;

import com.api.notice.dto.request.CreateNoticeRequest;
import com.api.notice.util.audit.BaseUserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "notice")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseUserEntity {

    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column(name = "title", length = 100)
    public String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Column(name = "read_count")
    private Long readCount;

    @Column(name = "delete_yn", length = 1)
    private String deleteYn;

    @Column(name = "delete_date_time")
    private LocalDateTime deleteDateTime;

    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    List<NoticeFile> noticeFiles = new ArrayList<>();

    public void addFile(NoticeFile noticeFile) {
        noticeFile.setNotice(this);
        this.noticeFiles.add(noticeFile);
    }

    public void removeFile(NoticeFile noticeFile) {
        this.noticeFiles.remove(noticeFile);
    }

    @Builder(builderMethodName = "create")
    private Notice(CreateNoticeRequest createNoticeRequest) {
        this.title = createNoticeRequest.title();
        this.content = createNoticeRequest.content();
        this.startDateTime = createNoticeRequest.getStartDateTime();
        this.endDateTime = createNoticeRequest.getEndDateTime();
        this.readCount = 0L;
        this.deleteYn = "N";
    }

    public void update(String title, String content, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.content = content;
        this.startDateTime = startDate;
        this.endDateTime = endDate;
    }

    public void addReadCount() {
        this.readCount++;
    }

    public void delete() {
        this.deleteYn = "Y";
        this.deleteDateTime = LocalDateTime.now();
    }

}
