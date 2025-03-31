package firstproject.probe.controller;

import firstproject.probe.model.Prompt;
import firstproject.probe.model.User;
import firstproject.probe.security.UserPrincipal;
import firstproject.probe.service.PromptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Prompt", description = "프롬프트 엔지니어링 API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/prompts")
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;

    @Operation(summary = "프롬프트 생성", description = "새로운 프롬프트를 생성하고 ChatGPT로부터 응답을 받습니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "프롬프트 생성 성공",
            content = @Content(schema = @Schema(implementation = Prompt.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @PostMapping
    public ResponseEntity<Prompt> createPrompt(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody Map<String, String> request) {
        
        User user = userPrincipal.getUser();
        String promptText = request.get("promptText");
        String question = request.get("question");
        
        Prompt prompt = promptService.createPrompt(user, promptText, question);
        return ResponseEntity.ok(prompt);
    }

    @Operation(summary = "사용자의 프롬프트 목록 조회", description = "현재 로그인한 사용자의 모든 프롬프트를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @GetMapping
    public ResponseEntity<List<Prompt>> getUserPrompts(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        List<Prompt> prompts = promptService.getUserPrompts(user);
        return ResponseEntity.ok(prompts);
    }

    @Operation(summary = "특정 프롬프트 조회", description = "프롬프트 ID로 특정 프롬프트를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "404", description = "프롬프트를 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Prompt> getPrompt(
            @Parameter(description = "프롬프트 ID") @PathVariable Long id) {
        Prompt prompt = promptService.getPrompt(id);
        return ResponseEntity.ok(prompt);
    }

    @Operation(summary = "프롬프트 삭제", description = "특정 프롬프트를 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "삭제 성공"),
        @ApiResponse(responseCode = "404", description = "프롬프트를 찾을 수 없음")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrompt(
            @Parameter(description = "프롬프트 ID") @PathVariable Long id) {
        promptService.deletePrompt(id);
        return ResponseEntity.ok().build();
    }
} 