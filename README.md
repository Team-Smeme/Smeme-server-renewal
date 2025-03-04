# Smeem(스밈)

### 일기로 시작하는 외국어 훈련, 스밈 ✍️

<img width="908" alt="image" src="https://github.com/Team-Smeme/Smeme-server-renewal/assets/55437339/d375dc1c-abb0-4e2e-a6e5-aad074913114">

<br/><br/>

## Architecture
### Server Architecture
<img width="900" alt="image" src="https://github.com/user-attachments/assets/11e56163-abaa-47c6-af1c-e677897e12cd">

<br/>

### Hexagonal Architecture (based Multi-Module)
<img alt="image" width="600" src="https://github.com/user-attachments/assets/fc6d8bff-2cbc-499e-854a-4b5ef6c9a197"/>

<br/><br/>

## Tech Stack
- Java 17, Spring Boot 3.2.1, Spring Data JPA
- PostgreSQL, AWS(EC2, RDS), Docker
- Swagger
- Kakao/Apple Oauth, Discord WebHook, Firebase Messaging Service

<br/>

## Server Developer

|<img width="200" alt="image" src="https://avatars.githubusercontent.com/u/55437339?v=4" />|<img width="200" alt="image" src="https://github.com/Team-Smeme/Smeme-server-renewal/assets/55437339/e546c4ee-b991-4168-bd94-f97d296b095e" />|
|:--:|:--:|
|[김소현(thguss)](https://github.com/thguss)|[최윤한(unanchoi)](https://github.com/unanchoi)|[임주민](https://avatars.githubusercontent.com/u/76610340?v=4)|
|mvp ~ | mvp ~ sprint2|25.03.04 ~|

<br/>

### Article
- by @unanchoi
  - [Presentation Layer <-> Application Layer DTO 리팩토링](https://ntnb.tistory.com/35)

- by @thguss
  - [FCM으로 푸시알림 기능 구현하기](https://soso-hyeon.tistory.com/87)
  - [멀티모듈 구조 설계 기록](https://soso-hyeon.tistory.com/83)
  - [CI/CD 파이프라인 구축하기](https://soso-hyeon.tistory.com/119)

<br/>

## Convention

### Code
- 하나의 메서드(method) 길이 12줄, 깊이(depth) 3 이내로 작성합니다.
- Lombok의 val을 사용합니다.

<br/>

### Commit

| 태그 이름 | 설명 |
| --- | --- |
| FEAT | 새로운 기능을 추가할 경우 |
| FIX | 버그를 고친 경우 |
| CHORE | 짜잘한 수정 |
| DOCS | 문서 수정 |
| INIT | 초기 설정 |
| TEST | 테스트 코드, 리펙토링 테스트 코드 추가 |
| RENAME | 파일 혹은 폴더명을 수정하거나 옮기는 작업인 경우 |
| STYLE | 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우 |
| REFACTOR | 코드 리팩토링 |

<br/>

### Branch

<img width="500" alt="image" src="https://github.com/Team-Smeme/Smeme-server-renewal/assets/55437339/1699ec13-babc-48f7-a914-d5f1fc1d51dd" />

- **main** : 실제 서버(Production)에 출시하는 브랜치
- **develop** : 개발이 완료된 최신 브랜치, 개발 서버(Develop)에 배포
- **feature** : 각 기능을 개발하는 브랜치, 기능 개발 단위로 브랜치 생성, ${이름_#이슈번호}
- **hotfix** : 배포된 버전에서 발생한 버그를 수정하는 브랜치
