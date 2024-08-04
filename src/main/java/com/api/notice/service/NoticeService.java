package com.api.notice.service;

import com.api.notice.domain.Notice;
import com.api.notice.domain.NoticeFile;
import com.api.notice.dto.request.CreateNoticeRequest;
import com.api.notice.dto.request.MultipartFilesRequest;
import com.api.notice.dto.request.UpdateNoticeRequest;
import com.api.notice.dto.response.DetailNoticeResponse;
import com.api.notice.dto.response.NoticesResponse;
import com.api.notice.exception.BaseException;
import com.api.notice.repository.NoticeFileRepository;
import com.api.notice.repository.NoticeRepository;
import com.api.notice.util.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private static final String UPLOAD_PATH = "file";

    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;

    public BaseResponse getNotices(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        List<Notice> notices = noticeRepository.findByNotice(pageable).getContent();
        return BaseResponse.ofData(notices.stream().map(NoticesResponse::from).toList());
    }

    @Transactional
    public BaseResponse getDetailNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException("errors.notice.empty"));

        notice.addReadCount();
        noticeRepository.save(notice);

        return BaseResponse.ofData(DetailNoticeResponse.from(notice));
    }

    @Transactional
    public BaseResponse saveNotice(CreateNoticeRequest createNoticeRequest, MultipartFilesRequest files) {
        Notice notice = createNoticeRequest.toNoticeEntity();

        if (files.isNotEmpty()) {
            List<NoticeFile> noticeFiles = files.toNoticeFileEntities(UPLOAD_PATH);
            noticeFiles.forEach(notice::addFile);
            files.upload(UPLOAD_PATH);
        }

        noticeRepository.save(notice);

        return BaseResponse.ofDataAndMessage(notice.getNoticeId(), "success.save");
    }

    @Transactional
    public BaseResponse updateNotice(Long noticeId, UpdateNoticeRequest updateNoticeRequest, MultipartFilesRequest files) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException("errors.notice.empty"));

        if (files.isNotEmpty()) {
            List<NoticeFile> noticeFiles = files.toNoticeFileEntities(UPLOAD_PATH);
            noticeFiles.forEach(notice::addFile);
            files.upload(UPLOAD_PATH);
        }

        if (!updateNoticeRequest.deleteFileIds().isEmpty()) {
            updateNoticeRequest.deleteFileIds().forEach(id -> {
                NoticeFile noticeFile = noticeFileRepository.findById(id)
                        .orElseThrow(() -> new BaseException("fail.update"));
                notice.removeFile(noticeFile);
            });
        }

        notice.update(updateNoticeRequest.title(),
                updateNoticeRequest.content(),
                updateNoticeRequest.getStartDateTime(),
                updateNoticeRequest.getEndDateTime());

        noticeRepository.save(notice);

        return BaseResponse.ofMessage("success.update");
    }

    @Transactional
    public BaseResponse deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException("errors.notice.empty"));

        notice.delete();
        noticeRepository.save(notice);

        return BaseResponse.ofMessage("success.delete");
    }

}
