package firstproject.probe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import firstproject.probe.config.AbstractTestcontainers;
import firstproject.probe.dto.SignupRequest;
import firstproject.probe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerIntegrationTest extends AbstractTestcontainers {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 API 성공 테스트")
    void signup_Success() throws Exception {
        // given
        SignupRequest request = new SignupRequest();
        request.setUsername("integrationtest");
        request.setEmail("integration@test.com");
        request.setPassword("password123");

        // when & then
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(request.getUsername())))
                .andExpect(jsonPath("$.email", is(request.getEmail())))
                .andExpect(jsonPath("$.firstLogin", is(true)))
                .andExpect(jsonPath("$.onboardingCompleted", is(false)));
    }

    @Test
    @DisplayName("중복된 사용자명으로 회원가입 API 호출 시 실패")
    void signup_DuplicateUsername_Fails() throws Exception {
        // given
        SignupRequest request1 = new SignupRequest();
        request1.setUsername("duplicate");
        request1.setEmail("first@test.com");
        request1.setPassword("password123");

        SignupRequest request2 = new SignupRequest();
        request2.setUsername("duplicate");
        request2.setEmail("second@test.com");
        request2.setPassword("password123");

        // 첫 번째 회원가입
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isCreated());

        // 두 번째 회원가입 - 같은 사용자명
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("이미 사용중인 사용자명입니다")));
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입 API 호출 시 실패")
    void signup_DuplicateEmail_Fails() throws Exception {
        // given
        SignupRequest request1 = new SignupRequest();
        request1.setUsername("first");
        request1.setEmail("duplicate@test.com");
        request1.setPassword("password123");

        SignupRequest request2 = new SignupRequest();
        request2.setUsername("second");
        request2.setEmail("duplicate@test.com");
        request2.setPassword("password123");

        // 첫 번째 회원가입
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isCreated());

        // 두 번째 회원가입 - 같은 이메일
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("이미 사용중인 이메일입니다")));
    }

    @Test
    @DisplayName("유효하지 않은 요청으로 회원가입 API 호출 시 실패")
    void signup_InvalidRequest_Fails() throws Exception {
        // given
        SignupRequest request = new SignupRequest();
        // 유효하지 않은 데이터 (필드 누락)

        // when & then
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}