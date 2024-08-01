package com.api.notice.service;

import com.api.notice.domain.Notice;
import com.api.notice.dto.request.SaveNoticeRequest;
import com.api.notice.dto.response.DetailNoticeResponse;
import com.api.notice.repository.NoticeRepository;
import com.api.notice.util.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public BaseResponse getDetailNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("errors.notice.empty"));

        return BaseResponse.ofData(DetailNoticeResponse.from(notice));
    }

    @Transactional
    public BaseResponse saveNotice(SaveNoticeRequest saveNoticeRequest) {
        Notice notice = saveNoticeRequest.toEntity();
        noticeRepository.save(notice);
        return BaseResponse.ofMessage("success.save");
    }

    @Transactional
    public BaseResponse updateNotice(Long noticeId, SaveNoticeRequest saveNoticeRequest) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("errors.notice.empty"));

        notice.update(saveNoticeRequest.title(), saveNoticeRequest.content(), saveNoticeRequest.startDate(), saveNoticeRequest.endDate());

        noticeRepository.save(notice);

        return BaseResponse.ofMessage("success.update");
    }

    @Transactional
    public BaseResponse deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("errors.notice.empty"));

        noticeRepository.delete(notice);

        return BaseResponse.ofMessage("success.delete");
    }
}
