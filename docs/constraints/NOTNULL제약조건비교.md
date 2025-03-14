# NOT NULL 제약조건: 데이터베이스 vs 코드 수준 비교

## 개요
이 문서는 데이터베이스 스키마에서 NOT NULL 제약조건을 적용하는 방법과 애플리케이션 코드 수준에서 검증하는 방법을 비교합니다.

## 데이터베이스 수준의 NOT NULL 제약조건

### 장점
1. **데이터 무결성 보장**: 데이터베이스 수준에서 NULL 값 입력을 방지하여 근본적인 무결성 보장
2. **일관성 유지**: 애플리케이션이나 접근 방식에 관계없이 동일한 제약조건 적용
3. **성능**: 인덱싱 및 쿼리 최적화에 도움
4. **명확한 스키마 문서화**: 스키마만 봐도 필수 필드를 식별 가능

### 단점
1. **스키마 변경 어려움**: 제약조건 변경 시 스키마 변경 필요
2. **마이그레이션 복잡성**: 기존 NULL 데이터가 있는 경우 마이그레이션 복잡

## 코드 수준의 유효성 검사

### 장점
1. **유연성**: 비즈니스 규칙에 따라 검증 로직을 쉽게 변경 가능
2. **세밀한 제어**: 단순 NULL 체크 외에 다양한 검증 로직 구현 가능
3. **사용자 친화적 오류 메시지**: 더 상세하고 명확한 오류 메시지 제공 가능
4. **조건부 검증**: 특정 조건에서만 NOT NULL 적용 가능

### 단점
1. **데이터 무결성 위험**: 검증 코드를 우회하면 NULL 값이 DB에 저장될 수 있음
2. **일관성 부족**: 여러 애플리케이션이나 접근 방식에서 동일한 검증 로직 구현 필요
3. **중복 코드**: 여러 곳에서 동일한 검증 로직을 구현해야 할 수 있음

## 권장 접근법: 하이브리드 방식

가장 효과적인 접근법은 두 방법을 조합하는 하이브리드 방식입니다:

### 데이터베이스 수준에서 적용할 제약조건
- 주요 식별자(PK, FK)
- 비즈니스 로직에서 절대 NULL이 허용되지 않는 핵심 데이터
- 다양한 애플리케이션이나 접근 경로가 있는 데이터

### 코드 수준에서 적용할 검증
- 사용자 경험을 위한 친화적 오류 메시지가 필요한 경우
- 조건부 검증이 필요한 경우
- 복잡한 비즈니스 규칙에 따른 검증

## Spring Boot에서의 구현 방법

### 1. 데이터베이스 제약조건
```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
```

### 2. Bean Validation을 사용한 코드 수준 검증
```java
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;

public class UserDto {
    @NotNull(message = "사용자명은 필수입니다")
    private String username;
    
    @NotNull(message = "이메일은 필수입니다")
    @Email(message = "유효한 이메일 형식이 아닙니다")
    private String email;
    
    @NotNull(message = "비밀번호는 필수입니다")
    private String password;
}
```

### 3. MyBatis에서의 파라미터 검증
```xml
<insert id="insertUser">
    INSERT INTO users (username, email, password)
    VALUES (#{username}, #{email}, #{password})
</insert>
```

```java
@Validated
@Service
public class UserService {
    private final UserMapper userMapper;
    
    public void createUser(@Valid UserDto userDto) {
        userMapper.insertUser(userDto);
    }
}
```

## 결론
Probe 프로젝트에서는 핵심 데이터의 무결성을 보장하기 위해 데이터베이스 수준의 NOT NULL 제약조건을 적용하고, 사용자 경험을 개선하기 위한 세부적인 검증은 코드 수준에서 구현하는 하이브리드 접근법을 채택합니다.
