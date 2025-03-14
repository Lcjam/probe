package firstproject.probe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private boolean isFirstLogin;
    private boolean isOnboardingCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
