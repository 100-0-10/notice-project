package com.api.notice.fixture;

import com.api.notice.dto.request.MultipartFilesRequest;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MultipartFilesRequestFixture {

    public static MultipartFilesRequest create(MockMultipartFile... files) {
        return new MultipartFilesRequest(new ArrayList<>(List.of(files)));
    }

    public static MockMultipartFile createMock(String originalFileName, String content) {
        return new MockMultipartFile("files", originalFileName, "text/plain",
                content.getBytes(StandardCharsets.UTF_8));
    }
}
