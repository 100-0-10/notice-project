package com.api.notice.domain;

import com.api.notice.util.audit.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "notice_file")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeFile extends BaseTimeEntity {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column(name = "original_name", length = 100)
    private String originalName;

    @Column(name = "upload_name", length = 100)
    private String uploadName;

    @Column(name = "size")
    private Long size;

    @Column(name = "path", length = 100)
    private String path;

    @Column(name = "extension", length = 10)
    private String extension;

    @JoinColumn(name = "notice_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Notice notice;

    void setNotice(Notice notice) {
        this.notice = notice;
    }

    @Builder(builderMethodName = "create")
    private NoticeFile(String originalName, String uploadName, long size, String path, String extension) {
        this.originalName = originalName;
        this.uploadName = uploadName;
        this.size = size;
        this.path = path;
        this.extension = extension;
    }
}
