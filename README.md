# Smeem(스밈) Server

<img width="908" alt="image" src="https://github.com/Team-Smeme/Smeme-server-renewal/assets/55437339/d375dc1c-abb0-4e2e-a6e5-aad074913114">

<br/><br/>

- ✍️ **서비스 소개**: [서비스 소개 바로가기](https://linktr.ee/smeem)
- 📱 **iOS 다운로드**: [App Stroe 바로가기](https://apps.apple.com/kr/app/smeem-%EC%8A%A4%EB%B0%88-%EC%98%81%EC%9E%91-%EC%98%81%EC%96%B4%EC%9D%BC%EA%B8%B0-%EC%98%81%EC%96%B4%EB%85%B8%ED%8A%B8/id6450711685)
- 📱 **Android 다운로드**: [Play Store 바로가기](https://play.google.com/store/apps/details?id=com.sopt.smeem&hl=ko&gl=US)

<br/>

## 🧑‍💻 팀원 소개 (Team)

| [김소현](https://github.com/thguss) | [최윤한](https://github.com/unanchoi) |
|:---:|:---:|
|<img width="250" alt="image" src="https://avatars.githubusercontent.com/u/55437339?v=4" />|<img width="250" alt="image" src="https://github.com/Team-Smeme/Smeme-server-renewal/assets/55437339/e546c4ee-b991-4168-bd94-f97d296b095e" />|
| Server Lead Developer | Server Developer |
|- 프로젝트 구조 설계/리팩토링<br/>- 일기 관련 기능 구현|프로젝트 구조 설계/리팩토링<br/> - 인증, 회원 관련 기능 구현|
|[[아티클] FCM으로 푸시알림 기능 구현하기](https://soso-hyeon.tistory.com/87) <br/> [[아티클] 멀티모듈 구조 설계 기록](https://soso-hyeon.tistory.com/83)|[[아티클] Presentation Layer <-> Application Layer DTO 리팩토링](https://ntnb.tistory.com/35)|

<br/>

## ⚒️ 기술 스택 (Tech)
- Java, Spring Boot
- PostgreSQL, Spring Data JPA, QueryDSL
- AWS : EC2, RDS, Code Deploy, Nginx
- JUnit5
- Swagger
- Sentry, Discord WebHook

<br/>

## 🌳 인프라 아키텍처 (Infra Architecture)

<img width="600" alt="image" src="https://github.com/Team-Smeme/Smeme-server-renewal/assets/81692211/ba9bcd6d-0c33-4790-90c1-443b3b410baa" />

<br/>

## 🗂️ 프로젝트 구조 (Project Architecture) : 멀티모듈 구조 (Multi-Module)
Smeem 프로젝트는 단일 모듈 구조에서 시작하여, 코드 간의 의존도와 결합도를 줄여보고자 멀티모듈 구조의 프로젝트로 리팩토링을 진행했습니다.

<img width="600" alt="image" src="https://github.com/Team-Smeme/Smeme-server-renewal/assets/81692211/3dc1d246-bf8f-47f9-bfc6-b22158f6c26f" />

### smeem-api
- 클라이언트와 가장 직접적으로 소통하는 계층으로, API와 비즈니스 로직을 모아둔 모듈입니다.
- Controller, Service 파일을 포함합니다.
 
### smeem-batch
- batch 작업을 하는 코드를 모아둔 모듈입니다.
- scheduler 파일을 포함합니다.
 
### smeem-common
- 프로젝트 전반에 공통으로 사용하는 객체를 모아둔 모듈입니다.
- 멀티 모듈 프로젝트 구조의 의미를 극대화하기 위해 최소한의 코드만 포함했습니다.

### smeem-domain
- Domain(Model)과 Database를 호출하는 기능의 코드를 모아둔 모듈입니다.
- Entity, Repository 파일을 포함합니다.
 
### smeem-external
- Database를 제외한 외부 서비스를 호출하는 기능을 하는 모듈입니다.
- 소셜로그인(카카오, 애플), FCM, Discord Webhook의 외부 API를 호출합니다.

<br/>

## 📌 코드 컨벤션 (Code Convention)

### Code
- 하나의 메서드(method) 길이 12줄, 깊이(depth) 3 이내로 작성합니다.
- Lombok의 val을 사용합니다.

### Entity
- id 자동 생성 전략은 IDENTITY를 사용합니다.
- @NoArgsConstructor 사용 시 access를 PROTECTED로 제한합니다.

### Enum
- Enum 값은 import static 호출로 사용합니다.

### DTO
- Controller에서 요청/응답하는 DTO와 Service에서 사용하는 DTO를 분리합니다.
    - Layered Architecture를 엄격하게 준수합니다.
    - 확장/번경에 용이하게 합니다.
- 네이밍은 아래와 같이 정의합니다.
    - Controller DTO: `${Entity명}${복수형일 경우 List 추가}${행위 또는 상태}${Request/Response}`
    - Service DTO: `${Entity명}${복수형일 경우 List 추가}${행위 또는 상태}Service${Request/Response}`

### Response
- 요청 성공 시, BaseResponse<T>와 SuccessCode(인터페이스)의 구현체를 사용합니다.
- 예외 발생 시, Exception과 FailureCode(인터페이스)의 구현체를 사용합니다.

### Service, Repository
- DB를 호출하는 경우 메서드명에 save, find, update, delete 용어를 사용합니다.
- 비즈니스 로직일 경우 메서드명에 create, get, update, delete, 그 외 용어를 사용합니다.
- 복수형은 ${Entity명}s로 표현합니다.
- Service 파일이 비즈니스 로직 5개 이상으로 커지면 조회, 비조회(Transactional)로 클래스를 분리합니다.

<br/>

## 📌 커밋 컨벤션 (Commit Convention)

> **[태그] 제목의 형태**

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

## 🎋 브랜치 전략 (Branch Strategy)

<img width="500" alt="image" src="https://github.com/Team-Smeme/Smeme-server-renewal/assets/55437339/1699ec13-babc-48f7-a914-d5f1fc1d51dd" />

- **main** : 실제 서버(Production)에 출시하는 브랜치
- **develop** : 개발이 완료된 최신 브랜치, 개발 서버(Develop)에 배포
- **feature** : 각 기능을 개발하는 브랜치, 기능 개발 단위로 브랜치 생성, ${이름_#이슈번호}
- **hotfix** : 배포된 버전에서 발생한 버그를 수정하는 브랜치
