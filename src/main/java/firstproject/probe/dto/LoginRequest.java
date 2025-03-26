package firstproject.probe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    
    @NotBlank(message = "사용자명 또는 이메일을 입력해주세요")
    private String usernameOrEmail;
    
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
