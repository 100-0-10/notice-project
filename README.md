

# Spring Boot Restful API

###
- Java 17
- Spring boot 3.3.2
- Postgresql Latest
- Docker

### 실행방법
우선, os에 종속되지 않게 도커로 데이터베이스를 실행하도록 했습니다. <br/>
도커 엔진이 실행되고 있으면 스프링 부트 프로젝트를 실행할 때, 도커 컴포즈에 의해 자동으로 이미지가 다운되고 실행됩니다. <br/>
도커에서 postgresql 이 실행되어 있어야 테스트코드가 실행됩니다.

##### 도커 다운로드
https://www.docker.com/products/docker-desktop/

##### 도커 컴포즈 실행
- docker-compose.yml 파일이 있는 디렉토리에서 아래 명령어 실행
```
docker-compose up -d
```
##### 또는
- 스프링 부트 실행 시, 도커 컴포즈가 실행되도록 설정되어 있습니다.


## 전략 및 설계방법

- Restful API 로 만들었으며, Get, Post, Patch, Delete HttpMethod 를 사용하여 조회, 등록, 수정, 삭제를 구현했습니다.
- @Valid 를 사용하여 유효성체크를 간단하게 사용 할 수 있게 했으며, 예외 발생 시 공통 예외 핸들러로 BaseResponse 클래스로 응답을 단일화 했습니다.
@NotDateTime 어노테이션을 커스텀해 생성했습니다. <br/>컨트롤러에서 폼데이터를 요청받을 때, 날짜 형식도 문자열로 들어오기 때문에 날짜 포맷이 아닌 경우 DateTimeParseException 예외를 던지도록 했습니다.
- BaseException 을 만들어 예외를 공통화 했으며, BaseResponse 클래스로 응답을 단일화 했습니다.
- BaseRestControllerAdvice 공통 예외 핸들링 클래스를 만들어 BaseException, MethodArgumentNotValidException, DateTimeParseException 클래스를 핸들링 했습니다.
예외 발생 시, BaseResponse 클래스로 응답을 단일화 했습니다.
- 유틸 클래스 BaseMessageSource 를 만들어 응답메시지는 MessageSource를 사용해 message.properties 에서 가져오도록 했습니다.
- BaseResponse 클래스를 만들어 응답을 단일화 할 수 있게 사용했습니다.
- 목록 조회는 페이징 처리를 해서 많은 데이터를 응답하지 않도록 했습니다.
- 상세 조회는 정해진 응답 포맷에 맞게 DetailNoticeResponse DTO를 응답했습니다.
- 등록은 등록에 필요한 데이터를 가진 CreateNoticeRequest DTO와, MultipartFile 을 리스트로 가지고 있는 일급컬렉션 클래스 MultipartFilesRequest 를 사용합니다.
- 수정은 수정에 필요한 데이터를 가진 UpdateNoticeRequest DTO와, MultipartFilesRequest, 식별자 noticeId 를 사용합니다.
첨부파일 수정은 새로운 파일을 먼저 등록한 후, UpdateNoticeRequest 의 deleteFileIds 필드에서 삭제할 파일의 식별자로 삭제합니다.
- 삭제는 식별자 noticeId 를 사용합니다.
- DTO 클래스는 record 클래스를 사용해 보일러플레이트 코드를 줄이고, 불변성을 유지하도록 했습니다.
- Notice, NoticeFile Entity 는 JPA 다대일 양방향 연관관계를 사용했으며, Notice에 연관관계 편의성 메서드를 만들었습니다.
  
## 테스트
- Junit5 를 사용하여 테스트를 작성했습니다.
- 테스트 실행 시 Docker postgresql 이 실행되어 있어야 합니다.
- fixture 패턴을 사용하여 테스트 데이터를 생성했습니다.
- @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT), rest-assured 라이브러리를 사용하여 컨트롤러 테스트를 작성했습니다.
- @ExtendWith(MockitoExtension.class) 을 사용하여 서비스 테스트를 작성했습니다.
- @DataJpaTest 를 사용하여 Repository 테스트를 작성했습니다.
