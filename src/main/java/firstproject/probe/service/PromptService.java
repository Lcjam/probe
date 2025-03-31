package firstproject.probe.service;

import firstproject.probe.model.Prompt;
import firstproject.probe.model.User;
import firstproject.probe.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptService {

    private final PromptRepository promptRepository;
    private final ChatGPTService chatGPTService;

    @Transactional
    public Prompt createPrompt(User user, String promptText, String question) {
        String answer = chatGPTService.generateResponse(question);
        
        Prompt prompt = Prompt.builder()
                .user(user)
                .promptText(promptText)
                .question(question)
                .answer(answer)
                .build();
        
        return promptRepository.save(prompt);
    }

    @Transactional(readOnly = true)
    public List<Prompt> getUserPrompts(User user) {
        return promptRepository.findByUserOrderByCreatedAtDesc(user);
    }

    @Transactional(readOnly = true)
    public Prompt getPrompt(Long id) {
        return promptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("프롬프트를 찾을 수 없습니다."));
    }

    @Transactional
    public void deletePrompt(Long id) {
        promptRepository.deleteById(id);
    }
}
