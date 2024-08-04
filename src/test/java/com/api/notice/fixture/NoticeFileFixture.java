package com.api.notice.fixture;

import com.api.notice.domain.Notice;
import com.api.notice.domain.NoticeFile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.Normalizer;
import java.util.UUID;

public class NoticeFileFixture {

    public static NoticeFile create(MockMultipartFile mockMultipartFile) {
        return NoticeFile.create()
                .originalName(getOriginalFileName(mockMultipartFile.getOriginalFilename()))
                .uploadName(getUploadFileName(mockMultipartFile.getOriginalFilename()))
                .path("file"+File.separator+getUploadFileName(mockMultipartFile.getOriginalFilename()))
                .extension(getExtension(mockMultipartFile.getOriginalFilename()))
                .size(mockMultipartFile.getSize())
                .build();
    }

    public static NoticeFile create(Long id, MockMultipartFile mockMultipartFile, Notice notice) {
        return new NoticeFile(id, getOriginalFileName(mockMultipartFile.getOriginalFilename()),
                getUploadFileName(mockMultipartFile.getOriginalFilename()),
                mockMultipartFile.getSize(),
                "file"+File.separator+getUploadFileName(mockMultipartFile.getOriginalFilename()),
                getExtension(mockMultipartFile.getOriginalFilename()),
                notice);
    }

    private static String getOriginalFileName(String originalFileName) {
        return Normalizer.normalize(originalFileName, Normalizer.Form.NFC);
    }

    private static String getUploadFileName(String originalFileName) {
        return UUID.randomUUID() + "." + getExtension(originalFileName);
    }

    private static String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

}
