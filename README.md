## Git branch 전략

---

| 브랜치 | 역할 | 규칙 |
| --- | --- | --- |
| main | 운영 환경 |  |
| dev | 개발 환경 | 최신 main 브랜치에서 만든다. main 브랜치에 머지하면 최신 코드를 유지 한다. |
| feat/14/기능 | 신규 기능 개발 | - 최신 dev 브랜치에서 만든다.  - issue 번호로 브랜치를 생성한다. - dev 브랜치에서 머지하면 삭제한다.|
- [이슈 기반 브랜치 전략을 사용한다.](https://junshock5.tistory.com/82)
- 네이밍 규칙
  - **EX) fix/#10[이슈 번호]/login**
- 저장소 하나를 다 같이 사용한다.
- 개발할 브랜치를 dev 아래에서 생성하고 dev로 pr한다.
- 운영에 배포할 것들은 dev → main으로 pr한다.
- pr 요청 시 assinge, milestone, label을 등록한다.(이슈 등록과 동일)
- [`머지되면 브랜치는 자동 삭제된다.`](https://earth-95.tistory.com/101)
  - dev → main 병합 시 dev도 삭제되기 때문에 아직 적용하지 못했다.
    알아내면 적용해야한다.
- 실제 운영 배포 시 main을 pull 받아 자동화한다.
- [github 셋팅에서 dev와 main은 push를 막는다.](https://hong-dev.github.io/bftest/master_branch/)

## 커밋 컨벤션

---

**[앵귤러 코드 컨벤션 참고](https://docs.google.com/document/d/1QrDFcIiPjSLDn3EL15IJygNPiHORgU1_OOAqWjiDU5Y/edit#heading=h.em2hiij8p46d)**

- feat : 새로운 기능 추가
- fix : 버그 수정
- refactor : 코드 리팩토링
- docs : 문서 관련
- test : 테스트 추가 및 수정
- etc : 빌드 관련, CI 설정, 스타일 설정, 그 외

`EX) feat: 커밋 메세지`
→ 이슈 번호는 편하게 링크를 타기 위해 사용한다.

## 이슈 컨벤션

---

- 한 주 단위로 **마일 스톤**을 작성한다.
  - 팀원이 같이 작성한다.

<img width="507" alt="스크린샷 2022-03-24 오후 1 22 51" src="https://user-images.githubusercontent.com/53487385/159841749-cf028430-d685-4f0a-b4e9-c57b47c4cd8b.png">


- 마일 스톤에 한 주에 해결할 이슈를 등록한다.
  - 버그나 리팩토링만 하는 것이 아닌 신규 기능 개발 등 모든 것들을 등록한다.
- 이슈 등록 전 아래 항목을 반드시 확인한다.
  - Milestone
  - Labels(커밋 컨벤션)
  - Assignees
- 이슈 등록 네이밍 규칙
  - 커밋 컨벤션/기능
    **EX) fix/회원가입이 안되는 현상 개선하기**

## PR 컨벤션

---

- [GitHub에 정의된 템플릿을 활용해 PR을 등록합니다.](https://github.com/woowacourse-teams/2021-pick-git/tree/develop/.github)
- PR 등록 전에 다음 항목들을 **반드시** 체크합니다.
  - Assignees
  - Labels(커밋 컨벤션)
  - Linked issues(해결하지 못한 사항)
    - [PR이 Merge되면 관련 Issue는 자동으로 Closed된다.](https://docs.github.com/en/issues/tracking-your-work-with-issues/linking-a-pull-request-to-an-issue)
      - `이슈 삭제가 안되고 브랜치가 삭제되는 현상으로 해결하지 못함.`
- 제목은 관련 이슈 번호를 명시하며, 명사형으로 작성합니다.
  - `EX) feat/README 기술 스택 및 서비스 설명을 추가`
- PR 내용에 `#이슈 번호`를 달아준다.
  - 편하게 링크를 타기 위함.
- merge할 때 브랜치를 삭제한다.
- merge는 Assignees가 모두 merge를 해야 merge가 된다.
  - `미해결 부분`

## 코드 컨벤션

---

[캠퍼스 핵데이 Java 코딩 컨벤션](https://naver.github.io/hackday-conventions-java/)

## 개발 프로세스 정리

---

1. 주차 별로 마일스톤을 생성한다. 매주 금, 토, 일 중 다 같이 생성한다.

<img width="777" alt="스크린샷 2022-03-24 오후 1 24 59" src="https://user-images.githubusercontent.com/53487385/159841937-9a184fc0-0e5c-47f6-a785-54a8a3adb51e.png">


2. 마일스톤에 해당 주차에 해결할 이슈를 등록한다.
- **EX) feat/이슈 테스트**

  <img width="775" alt="스크린샷 2022-03-24 오후 1 25 29" src="https://user-images.githubusercontent.com/53487385/159841988-968682a0-e113-4af3-b70f-d2eb3792c1a2.png">


`이슈 생성 시 우측 Assignees, Labels, Milestone을 반드시 확인한다.`
본인 개발사항이라면 `Assignees`은 본인을 등록하면 된다.

1. 이슈 단위로 브랜치를 생성한다.
- **EX) fix/10[이슈 번호]/login[내용]**
2. 커밋을 한다. 커밋 내용은 아래와 같다.
- `feat: 커밋 메세지`
- 이슈 번호는 편하게 링크를 타기 위함이다.
3. 이슈 브랜치 → dev로 PR한다.
- `EX) feat/README 기술 스택 및 서비스 설명을 추가[내용]`
- PR 내용에는 `#이슈 번호`를 작성한다. 리뷰어가 이슈 확인을 편하게 하기 위함이다.
- 이슈 등록과 동일하게 `Assignees, Labels, Milestone`을 확인한다.

  <img width="739" alt="스크린샷 2022-03-24 오후 1 26 16" src="https://user-images.githubusercontent.com/53487385/159842078-cb8c7a46-df24-42c8-a003-ceb2c525f952.png">


4. Assignees는 merge를 진행한다.
- `Assignees가 두 명 이상일 경우 모두 merge를 수락해야 merge가 되어야 하는 것 같은데 고민해봐야할 부분이다.`
- [merge가 되면 해당 브랜치는 자동을 삭제](https://earth-95.tistory.com/101)되어야 하는 것이 목표이다.  
  문제는 dev → main 병합 시 dev가 삭제되기 때문에 해결할 목표이다.
5. 운영 서버에 배포를 하려면 dev → main으로 PR하고, 운영 서버에서 main을 pull하여 자동화 배포를 한다.