# 4단계 - 수강신청(요구사항 변경)
***
## 코드 리뷰
> PR 링크:
> **[https://github.com/next-step/java-lms/pull/826](https://github.com/next-step/java-lms/pull/826)**

## 나의 학습 목표
- TDD 사이클로 구현
- 객체지향 생활 체조 원칙 준수
- 테스트 작성하기 쉬운 구조로 설계
- 자기 점검 체크리스트 준수 

## 핵심 학습 목표
- DB 테이블이 변경될 때도 스트랭귤러 패턴을 적용해 점진적인 리팩터링을 연습한다.
  - [스트랭글러(교살자) 패턴 - 마틴 파울러](https://martinfowler.com/bliki/StranglerFigApplication.html)
  - [스트랭글러 무화가 패턴](https://learn.microsoft.com/ko-kr/azure/architecture/patterns/strangler-fig)

## 변경된 기능 요구사항
#### 강의 수강신청은 강의 상태가 모집중일 때만 가능하다.
- 강의가 진행중인 상태에서도 수강신청이 가능해야 한다.
  - 강의 진행 상태(준비중, 진행중, 종료)와 모집상태(비모집중, 모집중)로 상태 값을 분리해야 한다.
#### 강의는 강의 커버 이미지 정보를 가진다.
- 강의는 **하나 이상**의 커버 이미지를 가질 수 있다.
#### 강사가 승인하지 않아도 수강 신청하는 모든 사람이 수강 가능하다.
- 우아한테크코스(무료), 우아한테크캠프 Pro(유로)와 같이 선발된 인원만 수강 가능해야 한다.
  - 강사는 수강신청한 사람 중 선발된 인원에 대해서만 수강 승인이 가능해야 한다.
  - 강사는 수강신청한 사람 중 선발되지 않은 사람은 수강을 취소할 수 있어야 한다.

## 프로그래밍 요구사항
- 리팩터링할 때 컴파일 에러와 기존의 단위 테스트의 실패를 최소화하면서 점진적인 리팩터링이 가능하도록 한다.
- DB 테이블에 데이터가 존재한다는 가정하에 리팩터링해야 한다.
  - 즉, 기존에 쌓인 데이터를 제거하지 않은 상태로 리팩터링 해야 한다

## PR 전 점검
**[체크리스트 확인하기](checklist.md)**

## 구현 기능 목록
#### Course
- [x] 강의 추가
- [x] 강의 개수 조회

#### Sessions (일급 컬렉션)
- [x] 강의 추가
- [x] 강의 개수 조회

#### Session
- [x] 수강 신청
  - [x] 모집중 상태 검증
  - [x] Policy에 검증 위임
- [x] 수강 승인
- [x] 수강 취소
- [x] 수강 인원 조회
- [x] 강의 타입 조회 (Policy에서 반환)

#### EnrollmentPolicy (추상 클래스, 템플릿 메서드 패턴)
- [x] final validate(payment, currentCount) - 알고리즘 순서 강제
- [x] 결제 검증 (validatePayment) - 하위에서 구현
- [x] 정원 검증 (validateCapacity) - 하위에서 구현
- [x] 강의 타입 반환 (getType) - 하위에서 구현

#### FreeEnrollmentPolicy
- [x] EnrollmentPolicy 상속
- [x] 결제 검증: 빈 구현 (무료)
- [x] 정원 검증: 빈 구현 (제한 없음)
- [x] 타입 반환: FREE

#### PaidEnrollmentPolicy
- [x] EnrollmentPolicy 상속
- [x] 결제 금액 검증 (수강료 일치)
- [x] 수강 인원 검증 (정원 초과)
- [x] 타입 반환: PAID

#### Enrollments (일급 컬렉션)
- [x] 수강 신청 추가
- [x] 수강 인원 조회
- [x] 중복 수강 신청 검증
- [x] 승인
- [x] 취소

#### Enrollment
- [x] 생성 시 PENDING 상태
- [x] 승인 → APPROVED
- [x] 취소 → REJECTED

#### EnrollmentStatus (Enum)
- [x] 수강 신청 승인 여부

#### CoverImages (일급 컬렉션)
- [x] 1개 이상 필수 검증
- [x] 여러 이미지 관리

#### CoverImage (VO)
- [x] 이미지 파일 정보와 크기 정보 조합 생성

#### ImageFile (VO)
- [x] 파일 크기 검증 (1MB 이하)
- [x] 확장자 추출
- [x] 이미지 타입 변환

#### ImageDimension (VO)
- [x] 너비/높이 최소값 검증 (300x200 이상)
- [x] 비율 검증 (3:2)

#### ImageType (Enum)
- [x] 확장자 → ImageType 변환 (from(String))
- [x] JPEG → JPG 변환 지원

#### SessionPeriod (VO)
- [x] 시작일/종료일 검증 (종료일 >= 시작일)

#### ProgressStatus (Enum) - 4단계 추가
- [x] 종료 여부 확인

#### RecruitmentStatus (Enum) - 4단계 추가
- [x] 수강 신청 가능 여부

#### Money (VO)
- [x] 금액 검증 (0 이상)
- [x] 금액 비교

#### Capacity (VO)
- [x] 최대 인원 검증 (1명 이상)
- [x] 초과 여부 확인

#### SessionRepository
- [x] Session 저장
- [x] Session 조회
- [x] courseId로 Session 목록 조회
- [x] Enrollment 저장
- [x] Enrollment 업데이트

#### SessionService
- [x] 수강 신청
- [x] 수강 승인
- [x] 수강 취소