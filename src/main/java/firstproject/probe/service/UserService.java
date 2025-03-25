package firstproject.probe.service;

import firstproject.probe.dto.SignupRequest;
import firstproject.probe.dto.UserResponse;
import firstproject.probe.model.User;
import firstproject.probe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    // MyBatis Mapper 의존성 제거
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public UserResponse signup(SignupRequest request) {
        // JPA를 사용한 중복 확인
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용중인 사용자명입니다");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다");
        }
        
        // 비밀번호 암호화 및 사용자 생성
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isFirstLogin(true)
                .isOnboardingCompleted(false)
                .build();
        
        // JPA를 사용하여 사용자 저장
        user = userRepository.save(user);
        
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isFirstLogin(user.isFirstLogin())
                .isOnboardingCompleted(user.isOnboardingCompleted())
                .build();
    }
}
