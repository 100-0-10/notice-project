package com.api.notice.unit;

import com.api.notice.domain.Notice;
import com.api.notice.domain.NoticeFile;
import com.api.notice.fixture.NoticeFileFixture;
import com.api.notice.fixture.NoticeFixture;
import com.api.notice.repository.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@DisplayName("공지사항_레포지토리_테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    @DisplayName("공지사항_생성")
    void createNotice() {
        // given
        Notice fixture = NoticeFixture
                .create("제목입니다",
                        "내용입니다",
                        "2024-08-01 00:00:00",
                        "2024-08-31 23:59:59");

        MockMultipartFile mockMultipartFile1 =
                new MockMultipartFile("file", "test1.txt", "text/plain", "test file1".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile mockMultipartFile2 =
                new MockMultipartFile("file", "test2.txt", "text/plain", "test file2".getBytes(StandardCharsets.UTF_8));

        NoticeFile noticeFile1 = NoticeFileFixture.create(mockMultipartFile1);
        NoticeFile noticeFile2 = NoticeFileFixture.create(mockMultipartFile2);

        fixture.addFile(noticeFile1);
        fixture.addFile(noticeFile2);

        // when
        Notice savedNotice = noticeRepository.save(fixture);

        // then
        assertAll(
                () -> assertThat(savedNotice.getNoticeId()).isNotNull(),
                () -> assertThat(savedNotice.getTitle()).isEqualTo(fixture.getTitle()),
                () -> assertThat(savedNotice.getContent()).isEqualTo(fixture.getContent()),
                () -> assertThat(savedNotice.getStartDateTime()).isEqualTo(fixture.getStartDateTime()),
                () -> assertThat(savedNotice.getEndDateTime()).isEqualTo(fixture.getEndDateTime()),

                () -> assertThat(savedNotice.getNoticeFiles().get(0).getFileId()).isNotNull(),
                () -> assertThat(savedNotice.getNoticeFiles().get(0).getOriginalName()).isEqualTo(noticeFile1.getOriginalName()),
                () -> assertThat(savedNotice.getNoticeFiles().get(0).getUploadName()).isEqualTo(noticeFile1.getUploadName()),

                () -> assertThat(savedNotice.getNoticeFiles().get(1).getFileId()).isNotNull(),
                () -> assertThat(savedNotice.getNoticeFiles().get(1).getOriginalName()).isEqualTo(noticeFile2.getOriginalName()),
                () -> assertThat(savedNotice.getNoticeFiles().get(1).getUploadName()).isEqualTo(noticeFile2.getUploadName())
        );
    }

    @Test
    @DisplayName("공지사항_조회")
    void getDetailNotice() {
        // given
        Notice fixture = NoticeFixture
                .create("제목입니다",
                        "내용입니다",
                        "2024-08-01 00:00:00",
                        "2024-08-31 23:59:59");

        // when
        noticeRepository.save(fixture);
        Notice findNotice = noticeRepository.findById(fixture.getNoticeId())
                .orElse(fixture);

        // then
        assertAll(
                () -> assertThat(findNotice.getNoticeId()).isEqualTo(fixture.getNoticeId()),
                () -> assertThat(findNotice.getTitle()).isEqualTo(fixture.getTitle()),
                () -> assertThat(findNotice.getContent()).isEqualTo(fixture.getContent()),
                () -> assertThat(findNotice.getStartDateTime()).isEqualTo(fixture.getStartDateTime()),
                () -> assertThat(findNotice.getEndDateTime()).isEqualTo(fixture.getEndDateTime())
        );
    }

    @Test
    @DisplayName("공지사항_수정")
    void updateNotice() {
        // given
        Notice fixture = NoticeFixture
                .create("제목입니다",
                        "내용입니다",
                        "2024-08-01 00:00:00",
                        "2024-08-31 23:59:59");

        // when
        noticeRepository.save(fixture);
        Notice findNotice = noticeRepository.findById(fixture.getNoticeId())
                .orElse(fixture);

        String updateTitle = "제목입니다2";
        String updateContent = "내용입니다2";
        LocalDateTime updateStartDateTime = LocalDateTime.of(2024, 9, 1, 0, 0, 0);
        LocalDateTime updateEndDateTime = LocalDateTime.of(2024, 9, 30, 23, 59, 59);

        findNotice.update(updateTitle, updateContent, updateStartDateTime, updateEndDateTime);

        // then
        assertAll(
                () -> assertThat(findNotice.getTitle()).isEqualTo(updateTitle),
                () -> assertThat(findNotice.getContent()).isEqualTo(updateContent),
                () -> assertThat(findNotice.getStartDateTime()).isEqualTo(updateStartDateTime),
                () -> assertThat(findNotice.getEndDateTime()).isEqualTo(updateEndDateTime)
        );
    }

    @Test
    @DisplayName("공지사항_삭제")
    void deleteNotice() {
        // given
        Notice fixture = NoticeFixture
                .create("제목입니다",
                        "내용입니다",
                        "2024-08-01 00:00:00",
                        "2024-08-31 23:59:59");

        // when
        noticeRepository.save(fixture);
        Notice findNotice = noticeRepository.findById(fixture.getNoticeId())
                .orElse(fixture);

        findNotice.delete();
        noticeRepository.save(findNotice);

        // then
        assertAll(
                () -> assertThat(findNotice.getDeleteYn()).isEqualTo("Y"),
                () -> assertThat(findNotice.getDeleteDateTime()).isNotNull()
        );
    }

}
