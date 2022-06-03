## Contributors
|더기|수|
|:-:|:-:|
|<img src="https://avatars.githubusercontent.com/u/53487385?v=4" alt="더기 이미지 없음" width="100" height="100">|<img src="https://avatars.githubusercontent.com/u/54942017?v=4" alt="수 이미지 없음" width="100" height="100">|
|[monkeyDugi](https://github.com/monkeyDugi)|[soosue](https://github.com/soosue)|

## ATDD 블로그 프로젝트

**프로젝트 깃허브**: [https://github.com/BlogSquad/my_blog/tree/dev](https://github.com/BlogSquad/my_blog/tree/dev)

**API 문서**: [http://monkeydugiblog.shop/docs/index.html](http://monkeydugiblog.shop/docs/index.html)

**기술 블로그**: https://www.notion.so/Tech-Blog-8f62fea193ec4b929a3e0961bfcfa83b

**팀 컨벤션**: https://www.notion.so/48b4a6fbbf8a4e8c981dea38baa7628d

**로그인 프로세스**: https://island-airmail-e76.notion.site/fbe1491a9bea4a919257160314b95eb6

### 프로젝트 소개

2022.03.21 ~ 2022.05.21

- 블로그 서비스: [API 문서](http://monkeydugiblog.shop/docs/index.html) 참고
  - 게시글 CRUD
  - 게시글 목록 조회: 페이징 처리
  - 회원가입, 로그인: 스프링 인터셉터를 활용한 세션 기반 회원 관리
    → Jwt 도입 예정
  - 댓글,  대댓글 CRUD: 계층형으로 구현
  - 조회수 관리: Redis 도입
- ATDD 도입
  - 사용자의 요구사항을 명확히 하고, 사용자 경험 기반으로 테스를 작성하여, 개발자와 사용자 간
    잘못된 커뮤니케이션인한 잘못된 개발을 방지하기 위해 인수 테스트 방법론 도입
- TDD 도입
  - 복잡한 웹 서비스 로직에서 유연한 코드와 테스트를 작성하기 위해 도입
- 협업 및 프로젝트 관리 방식
  - 리뷰를 통한 코드 품질 향상 및 코드 이해도 증진
  - 리뷰를 원활하게 하기 위한 Slack 알림 도입
  - 개발 히스토리를 관리하기 위하여 개발 건은 이슈 단위로 분리
  - 상세 내용은 [팀 컨벤션](https://www.notion.so/48b4a6fbbf8a4e8c981dea38baa7628d) 참고
- 배포
  - AWS EC2 도입
  - 배포 자동화 예정(S3, Code Deploy, Traivs CI)
  
### ERD
<img width="945" alt="스크린샷 2022-06-03 오후 6 18 23" src="https://user-images.githubusercontent.com/53487385/171826459-20292112-cac7-495a-bdb6-e9682d3bb5b1.png">

## 기술 스택
### 인프라
- aws ec2

- aws rds
  - mariaDB: 10.6
### 빌드 툴
- gradle: 7.4
### 백엔드
- java: 11

- spring boot: 2.6
- spring data jpa: 2.6
- spring data redis: 2.6
- hibernate: 5.6
- spring rest docs: 2.0
  - Rest Assured
  - AsciiDoc

- Rest Assured: 3.3
- Junit: 5

## 테스트 실행 가이드
### redis 설치
도커로 설치 후 실행 가능합니다. (https://hub.docker.com/_/redis 참고)
``` shell
# 이미지 다운 (docker images 로 확인 가능)
$ docker pull redis

# 컨테이너로 레디스 실행 (--name: 컨테이너 이름 설정, -p: 포트 포워딩, -d: 백그라운드에서 실행)
$ docker run --name some-redis -p 6379:6379 -d redis

# redis-cli 접속
$ docker exec -it some-redis redis-cli

./gradlew test
```

## 로그인 방법
포스트 CUD, 댓글 CUD는 로그인이 필요한 서비스 API입니다.  
그렇기 때문에 로그인이 필요합니다. 프론트가 없기 때문에 직접 여러 정보를 넣어서 요청해야 합니다.
```shell
1. 깃허브 승인 코드 발급 요청
=> https://github.com/login/oauth/authorize?client_id=2806fff623a09f90353e&scope=user

2. 로그인 요청
=> 승인 코드 발급 요청 url(redirect url)로 바로 로그인 요청이 된다.

3. 로그인이 필요한 API 요청
=> redirect url에 있는 JSESSIONID를 사용하여 요청한다.
```

