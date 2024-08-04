package com.api.notice.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@DisplayName("공지사항_컨트롤러_테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @LocalServerPort
    private int port;

    private static final String URI = "/api/notices";

    private Long id;

    @BeforeAll
    public void setUP() {
        id = 1L;
    }

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
    @DisplayName("조회")
    void detail() {
        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(URI+"/{noticeId}", id)
                .then().log().all().extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @Test
    @Order(1)
    @DisplayName("등록")
    void save() {
        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.MULTIPART)
                .multiPart("files", "test1.txt", "test1 text".getBytes())
                .multiPart("files", "test2.txt", "test2 text".getBytes())
                .formParam("title", "제목입니다")
                .formParam("content", "내용입니다")
                .formParam("startDateTime", "2024-08-01 00:00:00")
                .formParam("endDateTime", "2024-08-31 23:59:59")
                .when().post(URI)
                .then().log().all().extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );

        id = Long.parseLong(response.jsonPath().get("data").toString());
    }

    @Test
    @Order(3)
    @DisplayName("수정")
    void update() {
        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.MULTIPART)
                .multiPart("files", "test3.txt", "test3 text".getBytes())
                .formParam("title", "제목입니다2")
                .formParam("content", "내용입니다2")
                .formParam("startDateTime", "2024-09-01 00:00:00")
                .formParam("endDateTime", "2024-09-30 23:59:59")
                .formParam("deleteFileIds", List.of())
                .when().patch(URI+"/{noticeId}", id)
                .then().log().all().extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @Test
    @Order(4)
    @DisplayName("삭제")
    void delete() {
// when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete(URI+"/{noticeId}", id)
                .then().log().all().extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

}
