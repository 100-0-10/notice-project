package com.api.notice.repository;

import com.api.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query("SELECT n FROM Notice n " +
            "WHERE n.deleteYn = 'N' " +
            "AND CURRENT_TIMESTAMP between n.startDateTime AND n.endDateTime")
    Page<Notice> findByNotice(Pageable pageable);
}
