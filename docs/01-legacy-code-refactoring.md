# 1단계 - 레거시 코드 리팩터링
***
## 코드 리뷰
> PR 링크:
> **[https://github.com/next-step/java-lms/pull/801](https://github.com/next-step/java-lms/pull/801)**
## 나의 학습 목표
- TDD 사이클로 구현
- 객체지향 생활 체조 원칙 준수
- 테스트 작성하기 쉬운 구조로 설계
- 자기 점검 체크리스트 준수
## 기능 요구사항
### 질문 삭제하기
- 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
- 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
- 답변이 없는 경우 삭제가 가능하다.
- 질문자와 답변 글의 모든 답변자가 같은 경우 삭제가 가능하다.
- 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경한다.
- 질문자와 답변자가 다른 경우 답변을 삭제할 수 없다.
- 질문과 답변 삭제 이력에 대한 정보를 DeleteHistory를 활용해 남긴다.
## 리팩터링 요구사항
- nextstep.qna.service.QnaService의 deleteQuestion()는 앞의 질문 삭제 기능을 구현한 코드이다. 
- 이 메소드는 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드가 섞여 있다.
- QnaService의 deleteQuestion() 메서드에 단위 테스트 가능한 코드(핵심 비지니스 로직)를 도메인 모델 객체에 구현한다.
- QnaService의 비지니스 로직을 도메인 모델로 이동하는 리팩터링을 진행할 때 TDD로 구현한다.
- QnaService의 deleteQuestion() 메서드에 대한 단위 테스트는 src/test/java 폴더 nextstep.qna.service.QnaServiceTest이다. 도메인 모델로 로직을 이동한 후에도 QnaServiceTest의 모든 테스트는 통과해야 한다.
## 힌트
- 객체의 상태 데이터를 꺼내지(get)말고 메시지를 보낸다.
- 규칙 8: 일급 콜렉션을 쓴다.
- Question의 List를 일급 콜렉션으로 구현해 본다.
- 규칙 7: 3개 이상의 인스턴스 변수를 가진 클래스를 쓰지 않는다.
- 인스턴스 변수의 수를 줄이기 위해 도전한다.
- 도메인 모델에 setter 메서드 추가하지 않는다.
## PR 전 점검
**[체크리스트 확인하기](checklist.md)**
## 구현 기능 목록
### 도메인 모델 리팩터링
#### Answer
- [x] 삭제 상태 변경 (`delete()`)
- [x] 삭제 이력 생성 (`deleteHistory(LocalDateTime)`)
- [x] 소유자 확인 (`isOwner(NsUser)`)

#### Answers (일급 컬렉션)
- [x] 답변 추가 (`add(Answer)`)
- [x] 전체 답변 삭제 (`deleteAll()`)
- [x] 전체 답변 삭제 이력 생성 (`deleteHistories(LocalDateTime)`)
- [x] 삭제 가능 여부 검증 (`validateDeletable(NsUser)`)
    - 다른 사람이 쓴 답변이 있으면 예외 발생

#### Question
- [x] 삭제 (`delete(NsUser)`)
    - 소유자 검증
    - 답변 삭제 가능 여부 검증
    - 질문 삭제 상태 변경
    - 답변 삭제 상태 변경
- [x] 삭제 이력 생성 (`deleteHistories(LocalDateTime)`)
    - 질문 삭제 이력 생성
    - 답변 삭제 이력 생성
- [x] 답변 추가 (`addAnswer(Answer)`)

#### BaseEntity
- [x] 공통 필드 추출 (id, createdDate, updatedDate)

#### QuestionContent (VO)
- [x] 질문 내용 필드 추출 (title, contents)

### Service 리팩터링

#### QnAService
- [x] 삭제 로직을 도메인으로 위임
- [x] 삭제와 이력 생성 분리
    - `question.delete(loginUser)`
    - `question.deleteHistories(now)`

### 테스트

#### AnswerTest
- [x] `delete()` - 삭제 시 상태 변경
- [x] `deleteHistory()` - 삭제 이력 생성

#### AnswersTest
- [x] `validateDeletable_소유자일치()` - 검증 통과
- [x] `validateDeletable_소유자불일치_예외발생()` - 예외 발생

#### QuestionTest
- [x] `delete_성공()` - 질문 삭제 상태 변경
- [x] `delete_소유자불일치_예외발생()` - 예외 발생
- [x] `deleteHistories()` - 삭제 이력 생성

#### QnaServiceTest
- [x] 기존 테스트 모두 통과
