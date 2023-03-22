# Place

### stack
- java11
- Spring Boot 2.7.9
- gradle 6.8.1
---
## Start Guide
- 실행시 vm option으로 아래와 같이 추가합니다.
- profiles 추가시 globals_profiles.properties 파일을 추가합니다.
```bash
-Dspring.profiles.active=local  // globals_local.properties 리소스 사용
```
---
## Test Guide
- 토큰 발급 (권한 오류 발생 시)
curl -X POST "http://localhost:9000/token" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"email\": \"hmyu@kakao.com\", \"name\": \"유혜미\"}"

- 검색 키워드 목록 조회     
curl -H "Authorization:eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5NDg5MzA4NzM1LCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYTU5MjMwMjExMGU0NjBlODEyMTM1NTcyMjRhMzY3MSIsInVzZXJJbmZvIjp7InVzZXJVdWlkIjoiZmE1OTIzMDIxMTBlNDYwZTgxMjEzNTU3MjI0YTM2NzEiLCJlbWFpbCI6InRlc3QxQHRlc3QuY29tIiwibmFtZSI6Iu2FjOyKpO2KuDEifSwiZXhwIjoxNjc5NDk2NTA5fQ.Ob-ctxnxCbB2KRimOFeeZ_1APn_uUz5oIF92TO6nu4E" -X GET http://localhost:9000/v1/search/keyword

- 장소 검색 (키워드 변경 시 "keyword=키워드" 로 변경 필요)       
curl -H "Authorization:eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5NDg5MzA4NzM1LCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYTU5MjMwMjExMGU0NjBlODEyMTM1NTcyMjRhMzY3MSIsInVzZXJJbmZvIjp7InVzZXJVdWlkIjoiZmE1OTIzMDIxMTBlNDYwZTgxMjEzNTU3MjI0YTM2NzEiLCJlbWFpbCI6InRlc3QxQHRlc3QuY29tIiwibmFtZSI6Iu2FjOyKpO2KuDEifSwiZXhwIjoxNjc5NDk2NTA5fQ.Ob-ctxnxCbB2KRimOFeeZ_1APn_uUz5oIF92TO6nu4E" -G "http://localhost:9000/v1/search/place?data" --data-urlencode "keyword=제주스타벅스"
---
## 기타      
- spring.profiles.active / properties 리스너 등록
    - 서버 환경에 해당하는 DB정보와 전역 변수를 불러올 수 있도록 properties 파일을 로드 하도록 `CommonListener` 클래스에서 구현 
- Swagger 사용 
    - FE 와 협업할 수 있도록 API 명세를 확인/테스트 할 수 있도록 `SwaggerConfig` 클래스 추가
- jwt 토큰 사용
    - 사용자 정보를 담을 수 있고 API 요청에 대한 접근 권한을 줄 수 있도록 jwt 토큰 발급/검증 기능 추가
- aop
    - Controller 실행 전 실제 유효한 토큰인지 검증할 수 있도록 토큰 체크 aop `TokenCheckAspect` 클래스 구현
- ExceptionHandler
    - 어떤 오류가 발생하더라도 동일한 응답 구조가 내려갈 수 있도록 익셉션 오류를 핸들링 할 수 있는 `ExceptionControllerAdvice` 클래스 구현
- thirdparty 
    - 다른 써드파티 라이브러리를 추가해도 문제가 생기지 않도록 공통 통신 모듈인 `ApiSender` 클래스를 구현
    - 오류 발생 시 try-catch 로 RuntimeException 발생되지 않고 로직을 이어나갈 수 있도록 구현
- LinkedList
    - 장소 검색 시 순서를 보장할 수 있도록 LinkedList 로 구현
- @Transactional(readOnly = true)
    - 검색 키워드 목록 조회 시 읽기 전용 모드로 설정해 메모리 사용량을 최적화 할 수 있도록 구현
