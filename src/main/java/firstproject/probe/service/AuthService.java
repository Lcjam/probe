package firstproject.probe.service;

import firstproject.probe.dto.LoginRequest;
import firstproject.probe.dto.TokenResponse;
import firstproject.probe.dto.UserResponse;
import firstproject.probe.exception.AuthenticationException;
import firstproject.probe.model.User;
import firstproject.probe.repository.UserRepository;
import firstproject.probe.security.UserPrincipal;
import firstproject.probe.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Value("${app.jwt.expiration}")
    private long accessTokenValidityInMilliseconds;

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String accessToken = tokenProvider.createAccessToken(userPrincipal.getUsername());
            String refreshToken = tokenProvider.createRefreshToken(userPrincipal.getUsername());

            User user = userRepository.findById(userPrincipal.getId())
                    .orElseThrow(() -> new IllegalStateException("로그인한 사용자가 존재하지 않습니다"));
            
            // 첫 로그인이었을 경우, 첫 로그인 플래그 업데이트
            boolean wasFirstLogin = user.isFirstLogin();
            if (wasFirstLogin) {
                user.setFirstLogin(false);
                userRepository.save(user);
            }
            
            return TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(accessTokenValidityInMilliseconds / 1000)
                    .user(UserResponse.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .isFirstLogin(wasFirstLogin)  // 응답에는 변경 전 상태 반환
                            .isOnboardingCompleted(user.isOnboardingCompleted())
                            .build())
                    .build();
        } catch (Exception e) {
            throw new AuthenticationException("로그인에 실패했습니다: " + e.getMessage());
        }
    }

    @Transactional
    public void logout(String accessToken) {
        // 실제 로그아웃은 클라이언트에서 토큰을 삭제하는 방식으로 구현
        // 서버에서는 특별한 처리가 필요 없음
        // 추후 블랙리스트 관리가 필요하면 Redis 등을 사용하여 구현 가능
    }
}
