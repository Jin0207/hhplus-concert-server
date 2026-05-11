# 콘서트 예약 서비스

> 대기열 + 좌석 임시배정 + 포인트 충전식 결제 기반의 콘서트 예약 서비스.

## 문서
- [API 명세서](./docs/api-spec/api-spec.md)
- [ERD](./docs/erd/erd.md)
- 인프라 구성도: 추후 추가 예정

## 목표 시나리오

**대기열 기반 콘서트 예약**

1. 사용자는 대기열에 진입해 토큰을 발급받는다.
2. 활성(Active) 상태의 사용자만 좌석 조회/예약/결제 가능.
3. 좌석 예약 시 **임시배정(5분 TTL)** 이 설정되어 타 사용자가 접근 불가.
4. 임시배정 내 결제가 완료되면 확정, 아니면 만료되어 재판매 가능.
5. 결제 수단은 **포인트 충전식 결제** 를 사용.

## 기술 스택

- Java 17, Spring Boot 3.4.1, Spring Data JPA (MySQL)
- 테스트: JUnit5 + Testcontainers

## Getting Started

### Prerequisites

#### Running Docker Containers

`local` profile 로 실행하기 위하여 인프라가 설정되어 있는 Docker 컨테이너를 실행해주셔야 합니다.

```bash
docker-compose up -d
```