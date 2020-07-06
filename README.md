# 스프링부트로 구현하는 간단한 검색 서비스



## 애플리케이션 실행

애플리케이션을 실행하려면 카카오 오픈 API키가 **반드시** 필요합니다. 발급 방법은 [여기](https://developers.kakao.com/docs/latest/ko/getting-started/apphttps://developers.kakao.com/docs/latest/ko/getting-started/app)를 참고하세요.



### 직접빌드해서 실행

> src/main/resources/application.yml

위 경로 파일의 `{FIXME-INPUT-YOUR-KEY}` 부분을 발급받은 키로 교체 후 다음 명령어를 실행합니다.

```shell
$ ./gradlew bootRun
```



### 이미 빌드 된 jar 를 실행

[releases](https://github.com/onoctober/place-search/releases) 에서 최신 릴리즈 파일을 다운로드 받습니다.

```shell
$ java -jar place-search-*.jar --spring.profiles.active=local --kakao.key={이부분을발급받은키입력}
```






## 기능 사용하기

성공적으로 애플리케이션이 실행되었다면 제공 되는 기능을 사용할 수 있습니다.



### 기본 회원 정보 (local 환경시 해당)

일부 기능은 로그인을 필요로 합니다. 제공되는 기본 로그인 정보는 다음과 같습니다. 

- ryan / foobar
- apeach / foobar1
- frodo / foobar2



### IntelliJ 를 통한 기능 실행

프로젝트 루트에 위치한 `rest.http`를 통해 각 API 를 호출 할 수 있습니다.



### curl 을 통한 기능 실행

먼저 JSON 포맷의 데이터를 다루는 커맨드라인 유틸리티 [jq](https://www.44bits.io/ko/post/cli_json_processor_jq_basic_syntax#%EB%93%A4%EC%96%B4%EA%B0%80%EB%A9%B0-%EC%BB%A4%EB%A7%A8%EB%93%9C%EB%9D%BC%EC%9D%B8-json-%ED%94%84%EB%A1%9C%EC%84%B8%EC%84%9C-jq)를 설치합니다.

```shell
$ brew install jq
```



#### 로그인

```shell
$ TOKEN_TEMP=$(curl -X "POST" "http://localhost:8080/login" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -d $'{
  "id": "ryan",
  "password": "foobar"
}' | jq -r '.data.token')
```

로그인 성공시 `TOEKN_TEMP` 변수에 토큰이 저장됩니다.



#### 장소 검색

```shell
$ curl "http://localhost:8080/v1/search?page=1&size=10&keyword=kakaofriends" \
     -H 'Authorization: Bearer '"$TOKEN_TEMP"'' | jq
```



#### 인기 검색어

```shell
$ curl "http://localhost:8080/v1/trends" | jq
```





## 오픈소스 라이선스

- spring-boot
- embedded-redis: local 및 test 환경에서 사용
- h2database: local 및 test 환경에서 사용
- httpclient: http client connection pooling 을 위해 사용
- jjwt: jwt 기반 인증을 위해 사용.
- lombok: 반복되는 행사코드를 줄여 개발 편의를 위해 사용
- assertj: 단위 테스트 편의를 위해 사용