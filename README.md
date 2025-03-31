# Probe - AI 프롬프트 엔지니어링 플랫폼

Probe는 AI 프롬프트 엔지니어링을 위한 웹 기반 플랫폼입니다. 사용자들이 효과적인 프롬프트를 작성, 공유하고 관리할 수 있는 환경을 제공합니다.

## 주요 기능

- 🔐 사용자 인증
  - JWT 기반 로그인/회원가입
  - Mac 터미널 스타일의 UI/UX
  - 보안된 사용자 인증 시스템

- 🤖 프롬프트 관리
  - 프롬프트 작성 및 저장
  - 프롬프트 버전 관리
  - 프롬프트 공유 및 협업

- 📊 분석 및 최적화
  - 프롬프트 성능 분석
  - 사용 통계 및 인사이트
  - 최적화 추천

## 기술 스택

### 프론트엔드
- React.js
- React Router DOM
- Emotion (스타일링)
- Axios (HTTP 클라이언트)

### 백엔드
- Spring Boot
- Spring Security
- JWT Authentication
- JPA/Hibernate
- MySQL

### 인프라
- Docker
- Nginx
- AWS

## 시작하기

### 필수 조건
- Node.js 18.0.0 이상
- Java 17 이상
- Docker

### 설치 및 실행

1. 프로젝트 클론
```bash
git clone https://github.com/yourusername/probe.git
cd probe
```

2. 프론트엔드 실행
```bash
cd my-probe
npm install
npm start
```

3. 백엔드 실행
```bash
cd ../probe
./gradlew bootRun
```

4. Docker 실행
```bash
docker-compose up -d
```

## 개발 가이드

### 디렉토리 구조
```
probe/
├── my-probe/              # 프론트엔드
│   ├── src/
│   │   ├── components/    # React 컴포넌트
│   │   ├── services/     # API 서비스
│   │   └── styles/       # 스타일 파일
│   └── public/           # 정적 파일
│
└── src/                  # 백엔드
    └── main/
        └── java/
            └── firstproject/
                └── probe/
                    ├── config/     # 설정 파일
                    ├── controller/ # API 컨트롤러
                    ├── dto       
                    ├── exception
                    ├── mapper
                    ├── model
                    ├── repository
                    ├── security
                    ├── service/    # 비즈니스 로직
                    └── model/      # 데이터 모델
```

### API 문서
- Swagger UI: `http://localhost:8080/swagger-ui.html`

## 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 라이선스

이 프로젝트는 MIT 라이선스를 따릅니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 연락처

프로젝트 관리자 - [@yourusername](https://github.com/yourusername)

프로젝트 링크: [https://github.com/yourusername/probe](https://github.com/yourusername/probe)
