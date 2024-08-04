package com.api.notice.dto.request;

import com.api.notice.domain.NoticeFile;
import com.api.notice.exception.BaseException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.List;
import java.util.UUID;

public record MultipartFilesRequest(List<MultipartFile> files) {

    public List<NoticeFile> toNoticeFileEntities(String uploadPath) {
        return this.files.stream().map(file ->
                NoticeFile.create()
                        .originalName(getOriginalFileName(file.getOriginalFilename()))
                        .uploadName(getUploadFileName(file.getOriginalFilename()))
                        .path(uploadPath+File.separator+getUploadFileName(file.getOriginalFilename()))
                        .extension(getExtension(file.getOriginalFilename()))
                        .size(file.getSize())
                        .build()).toList();
    }

    private String getOriginalFileName(String originalFileName) {
        return Normalizer.normalize(originalFileName, Normalizer.Form.NFC);
    }

    private String getUploadFileName(String originalFileName) {
        return UUID.randomUUID() + "." + getExtension(originalFileName);
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    public void upload(String uploadPath) {
        this.files.forEach(file -> {
            try {
                file.transferTo(Path.of(uploadPath, getUploadFileName(file.getOriginalFilename())));
            } catch (IOException e) {
                throw new BaseException("errors.file.upload");
            }
        });
    }

    public boolean isNotEmpty() {
        return !this.files.isEmpty();
    }
}
