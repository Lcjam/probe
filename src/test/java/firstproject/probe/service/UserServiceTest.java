package firstproject.probe.service;

import firstproject.probe.dto.SignupRequest;
import firstproject.probe.dto.UserResponse;
import firstproject.probe.model.User;
import firstproject.probe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private SignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser");
        signupRequest.setEmail("test@example.com");
        signupRequest.setPassword("password123");
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signup_Success() {
        // given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        
        User savedUser = User.builder()
                .id(1L)
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password("encodedPassword")
                .isFirstLogin(true)
                .isOnboardingCompleted(false)
                .build();
        
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // when
        UserResponse response = userService.signup(signupRequest);

        // then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(signupRequest.getUsername(), response.getUsername());
        assertEquals(signupRequest.getEmail(), response.getEmail());
        assertTrue(response.isFirstLogin());
        assertFalse(response.isOnboardingCompleted());
        
        verify(userRepository).existsByUsername(signupRequest.getUsername());
        verify(userRepository).existsByEmail(signupRequest.getEmail());
        verify(passwordEncoder).encode(signupRequest.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("중복된 사용자명으로 회원가입 시 예외 발생")
    void signup_DuplicateUsername_ThrowsException() {
        // given
        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(true);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.signup(signupRequest));
        
        assertEquals("이미 사용중인 사용자명입니다", exception.getMessage());
        verify(userRepository).existsByUsername(signupRequest.getUsername());
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입 시 예외 발생")
    void signup_DuplicateEmail_ThrowsException() {
        // given
        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.signup(signupRequest));
        
        assertEquals("이미 사용중인 이메일입니다", exception.getMessage());
        verify(userRepository).existsByUsername(signupRequest.getUsername());
        verify(userRepository).existsByEmail(signupRequest.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }
}
