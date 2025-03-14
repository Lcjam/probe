package firstproject.probe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // 최신 방식으로 CSRF 설정
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/users/signup").permitAll()
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}
