package com.api.notice.unit;

import com.api.notice.domain.Notice;
import com.api.notice.domain.NoticeFile;
import com.api.notice.dto.request.CreateNoticeRequest;
import com.api.notice.dto.request.MultipartFilesRequest;
import com.api.notice.dto.request.UpdateNoticeRequest;
import com.api.notice.dto.response.DetailNoticeResponse;
import com.api.notice.exception.BaseException;
import com.api.notice.fixture.*;
import com.api.notice.repository.NoticeFileRepository;
import com.api.notice.repository.NoticeRepository;
import com.api.notice.service.NoticeService;
import com.api.notice.util.message.BaseMessageSource;
import com.api.notice.util.response.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@DisplayName("공지사항_서비스_테스트")
@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private NoticeFileRepository noticeFileRepository;

    @InjectMocks
    private NoticeService noticeService;

    private Notice notice;

    @BeforeEach
    void setUp() {
        notice = NoticeFixture.create(1L, "제목입니다",
                "내용입니다",
                LocalDateTime.of(2024, 9, 1, 0, 0, 0),
                LocalDateTime.of(2024, 9, 30, 23, 59, 59));

        NoticeFile file1 = NoticeFileFixture.create(1L,
                MultipartFilesRequestFixture.createMock("test1.txt", "test file1"), notice);
        NoticeFile file2 = NoticeFileFixture.create(2L,
                MultipartFilesRequestFixture.createMock("test2.txt", "test file2"), notice);

        notice.addFile(file1);
        notice.addFile(file2);
    }

    @Nested
    @DisplayName("조회")
    class detail {
        @Test
        @DisplayName("[성공] 상세조회")
        void getDetailSuccess() {
            // given
            Notice mockNotice = notice;
            when(noticeRepository.findById(1L)).thenReturn(Optional.of(mockNotice));

            // when
            BaseResponse baseResponse = noticeService.getDetailNotice(mockNotice.getNoticeId());

            // then
            DetailNoticeResponse findNotice = (DetailNoticeResponse) baseResponse.data();

            assertThat(findNotice.noticeId()).isEqualTo(notice.getNoticeId());
        }

        @Test
        @DisplayName("[예외] 조회실패_공지사항_없음")
        void getDetailFail() {
            // given
            Notice notice = NoticeFixture.create(2L, "제목입니다",
                    "내용입니다",
                    LocalDateTime.of(2024, 9, 1, 0, 0, 0),
                    LocalDateTime.of(2024, 9, 30, 23, 59, 59));

            // then
            assertThatThrownBy(() -> noticeService.getDetailNotice(notice.getNoticeId()))
                    .isInstanceOf(BaseException.class);
        }
    }

    @Nested
    @DisplayName("등록")
    class save {
        @Test
        @DisplayName("[성공] 등록")
        void saveSucess() {
            // given
            CreateNoticeRequest createNoticeRequest = CreateNoticeRequestFixture.create("제목입니다",
                    "내용입니다",
                    "2024-08-01 00:00:00", "2024-08-31 23:59:59");

            MockMultipartFile file1 = MultipartFilesRequestFixture.createMock("test1.txt", "test file1");
            MockMultipartFile file2 = MultipartFilesRequestFixture.createMock("test2.txt", "test file2");

            MultipartFilesRequest files = MultipartFilesRequestFixture.create(file1, file2);

            // when
            BaseResponse baseResponse = noticeService.saveNotice(createNoticeRequest, files);

            // then
            assertThat(baseResponse.message()).isEqualTo(BaseMessageSource.getMessage("success.save"));
        }
    }

    @Nested
    @DisplayName("수정")
    class update {
        @Test
        @DisplayName("[성공] 수정")
        void updateSuccess() {
            // given
            UpdateNoticeRequest updateNoticeRequest = UpdateNoticeRequestFixture.create("제목입니다",
                    "내용입니다",
                    "2024-08-01 00:00:00", "2024-08-31 23:59:59",
                    List.of(1L));

            MockMultipartFile file3 = MultipartFilesRequestFixture.createMock("test3.txt", "test file3");
            MultipartFilesRequest files = MultipartFilesRequestFixture.create(file3);

            when(noticeRepository.findById(1L)).thenReturn(Optional.of(notice));
            when(noticeFileRepository.findById(1L)).thenReturn(Optional.ofNullable(notice.getNoticeFiles().get(0)));

            // when
            BaseResponse baseResponse = noticeService.updateNotice(notice.getNoticeId(), updateNoticeRequest, files);

            // then
            assertThat(baseResponse.message()).isEqualTo(BaseMessageSource.getMessage("success.update"));
        }

        @Test
        @DisplayName("[예외] 수정실패_파일삭제_없음")
        void updateFail() {
            // given
            UpdateNoticeRequest updateNoticeRequest = UpdateNoticeRequestFixture.create("제목입니다",
                    "내용입니다",
                    "2024-08-01 00:00:00", "2024-08-31 23:59:59",
                    List.of(1L));

            MockMultipartFile file3 = MultipartFilesRequestFixture.createMock("test3.txt", "test file3");
            MultipartFilesRequest files = MultipartFilesRequestFixture.create(file3);

            when(noticeRepository.findById(1L)).thenReturn(Optional.of(notice));

            // then
            assertThatThrownBy(() -> noticeService.updateNotice(notice.getNoticeId(), updateNoticeRequest, files))
                    .isInstanceOf(BaseException.class);
        }
    }

    @Nested
    @DisplayName("삭제")
    class delete {
        @Test
        @DisplayName("[성공] 삭제")
        void deleteSuccess() {
            // given
            Notice mockNotice = notice;
            when(noticeRepository.findById(1L)).thenReturn(Optional.of(mockNotice));

            // when
            BaseResponse baseResponse = noticeService.deleteNotice(mockNotice.getNoticeId());

            // then
            assertThat(baseResponse.message()).isEqualTo(BaseMessageSource.getMessage("success.delete"));
        }

        @Test
        @DisplayName("[예외] 조회실패_공지사항_없음")
        void deleteFail() {
            // given
            Notice notice = NoticeFixture.create(2L, "제목입니다",
                    "내용입니다",
                    LocalDateTime.of(2024, 9, 1, 0, 0, 0),
                    LocalDateTime.of(2024, 9, 30, 23, 59, 59));

            // then
            assertThatThrownBy(() -> noticeService.deleteNotice(notice.getNoticeId()))
                    .isInstanceOf(BaseException.class);
        }
    }

}
