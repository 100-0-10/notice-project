package com.api.notice.unit;

import com.api.notice.domain.Notice;
import com.api.notice.repository.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    @DisplayName("햄스터")
    void saveNotice() {
        // given


        // when

        // then
    }



}
