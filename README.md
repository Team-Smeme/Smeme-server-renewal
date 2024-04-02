# Smeem 

![]()

일기로 시작하는 외국어 훈련, 외국어 학습 서비스 Smeem입니다. 외국어 일기 작성을 통해 일상 속 외국어 출력 훈련을 돕는 학습 서비스입니다. 
언제 어디서든 손쉬운 외국어 훈련을 큰 비용 없이 시작할 수 있습니다.

## 팀원 소개

| [김소현](https://github.com/thguss) | [최윤한](https://github.com/unanchoi) |
|:---:|:---:|
| ![image](https://avatars.githubusercontent.com/u/55437339?v=4) | ![image]("https://avatars.githubusercontent.com/u/81692211?v=4") |
| Server Lead Developer | Server Developer |

### 팀 아티클
| 링크 | 작성자 |
|:---:|:---:|
| [Presentation Layer <-> Application Layer DTO 리팩토링](https://ntnb.tistory.com/35) | 윤한 |


## 기술 스택
- Java, Spring Boot
- MySQL, Spring Data JPA, QueryDSL
- AWS : EC2, RDS, Code Deploy, Nginx
- JUnit5
- Swagger
- Sentry, Discord WebHook

## 인프라 아키텍처

![infra](https://github.com/Team-Smeme/Smeme-server-renewal/assets/81692211/ba9bcd6d-0c33-4790-90c1-443b3b410baa)

## 프로젝트 구조
Smeem 프로젝트는 단일 모듈 구조에서 시작하여, 코드 간의 의존도와 결합도를 줄여보기 위해 멀티모듈 프로젝트로 리팩토링을 진행했습니다.

![project-structure](https://github.com/Team-Smeme/Smeme-server-renewal/assets/81692211/601a2ab7-5872-4a41-8b3a-6639e1e78bcb)
![project-structure2](https://github.com/Team-Smeme/Smeme-server-renewal/assets/81692211/3dc1d246-bf8f-47f9-bfc6-b22158f6c26f)

 #### smeem-api
 - api와 관련된 모듈입니다.
 
 #### smeem-batch
 - batch 작업을 하는 코드를 모아둔 모듈입니다.
 
 #### smeem-common
 - 프로젝트 전반에 사용하는 객체를 모아둔 모듈입니다. common 모듈의 경우 멀티 모듈 프로젝트 구조의
 의미를 훼손하지 않기 위해 최소한의 코드만 모아뒀습니다.
 
 #### smeem-domain
 - Domain(Entity)과 Database를 호출하는 기능을 하고 있는 모듈입니다. 
 
 #### smeem-external
 - Database를 제외한 외부 서비스를 호출하는 기능을 하는 모듈입니다.
 - 소셜로그인, FCM, Discord Webhook과 관련된 기능을 하고 있는 모듈입니다.
 
### 📌 Code Convention

**Code**

- 하나의 메서드(method) 길이 12줄, 깊이(depth) 3 이내로 작성합니다.
- Lombok의 val을 사용합니다.

**Entity**

- id 자동 생성 전략은 IDENTITY를 사용합니다.
- @NoArgsConstructor 사용 시 access를 PROTECTED로 제한합니다.

**Enum**

- Enum 값은 import static 호출로 사용합니다.

**DTO**

- Controller에서 요청/응답하는 DTO와 Service에서 사용하는 DTO를 분리합니다.
    - Layered Architecture를 엄격하게 준수합니다.
    - 확장/번경에 용이하게 합니다.
- 네이밍은 아래와 같이 정의합니다.
    - Controller DTO: `${Entity명}${복수형일 경우 List 추가}${행위 또는 상태}${Request/Response}`
    - Service DTO: `${Entity명}${복수형일 경우 List 추가}${행위 또는 상태}Service${Request/Response}`

**Response**

- 요청 성공 시, BaseResponse<T>와 SuccessCode(인터페이스)의 구현체를 사용합니다.
- 예외 발생 시, Exception과 FailureCode(인터페이스)의 구현체를 사용합니다.

**Service, Repository**

- DB를 호출하는 경우 메서드명에 save, find, update, delete 용어를 사용합니다.
- 비즈니스 로직일 경우 메서드명에 create, get, update, delete, 그 외 용어를 사용합니다.
- 복수형은 ${Entity명}s로 표현합니다.
- Service 파일이 비즈니스 로직 5개 이상으로 커지면 조회, 비조회(Transactional)로 클래스를 분리합니다.
 
 ## 브랜치 전략
