package firstproject.probe.service;

import firstproject.probe.dto.SignupRequest;
import firstproject.probe.dto.UserResponse;
import firstproject.probe.mapper.UserMapper;
import firstproject.probe.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public UserResponse signup(SignupRequest request) {
        // 사용자명 중복 확인
        if (userMapper.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용중인 사용자명입니다");
        }
        
        // 이메일 중복 확인
        if (userMapper.existsByEmail(request.getEmail())) {
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
        
        userMapper.insert(user);
        
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isFirstLogin(user.isFirstLogin())
                .isOnboardingCompleted(user.isOnboardingCompleted())
                .build();
    }
}
