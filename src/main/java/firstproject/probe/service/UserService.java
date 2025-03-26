package firstproject.probe.service;

import firstproject.probe.dto.SignupRequest;
import firstproject.probe.dto.UserResponse;
import firstproject.probe.exception.DuplicateResourceException;
import firstproject.probe.exception.ResourceNotFoundException;
import firstproject.probe.model.User;
import firstproject.probe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public UserResponse signup(SignupRequest request) {
        // 사용자명 중복 체크
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("이미 사용 중인 사용자명입니다.");
        }
        
        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("이미 사용 중인 이메일입니다.");
        }
        
        // 비밀번호 암호화 및 사용자 생성
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isFirstLogin(true)
                .isOnboardingCompleted(false)
                .build();
        
        // 사용자 저장
        user = userRepository.save(user);
        
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isFirstLogin(user.isFirstLogin())
                .isOnboardingCompleted(user.isOnboardingCompleted())
                .build();
    }

    @Transactional
    public void withdraw(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));
        
        // 연관된 데이터들은 DB의 CASCADE 설정에 의해 자동으로 삭제됨
        userRepository.delete(user);
    }
}
