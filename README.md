## Contributors
|더기|수|
|:-:|:-:|
|<img src="https://avatars.githubusercontent.com/u/53487385?v=4" alt="더기 이미지 없음" width="100" height="100">|<img src="https://avatars.githubusercontent.com/u/54942017?v=4" alt="수 이미지 없음" width="100" height="100">|
|[monkeyDugi](https://github.com/monkeyDugi)|[soosue](https://github.com/soosue)|

## ATDD 기반의 웹 서비스 개발 프로젝트
### 프로젝트 방향
- 아주 간단한 블로그를 ATDD 기반으로 개발합니다.
- ATDD란 Acceptance Test Dirven Development입니다.
- 어떤 것을 만들지에 대한 프로젝트가 아닌, 프로젝트를 어떻게 효율적으로 개발할지 경험하는 프로젝트입니다.
  그 도구가 ATDD입니다.
- 사용자 스토리 기반의 개발 방법론으로 생산성을 높일 수 있습니다.
- [ATDD란?](https://island-airmail-e76.notion.site/ATDD-e1053c822c834ba8992e949164f5001d)

### 기능
- [API 문서 링크 참고](http://monkeydugiblog.shop/docs/index.html)

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
[=> 로그인 프로세스 링크](https://island-airmail-e76.notion.site/fbe1491a9bea4a919257160314b95eb6)

## 팀 블로그
- [팀 컨벤션 링크 참고](https://island-airmail-e76.notion.site/48b4a6fbbf8a4e8c981dea38baa7628d)
- [기술 블로그 링크 참고](https://island-airmail-e76.notion.site/Tech-Blog-8f62fea193ec4b929a3e0961bfcfa83b)

