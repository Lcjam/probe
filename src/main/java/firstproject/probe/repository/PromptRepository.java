package firstproject.probe.repository;

import firstproject.probe.model.Prompt;
import firstproject.probe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {
    List<Prompt> findByUser(User user);
    List<Prompt> findByUserOrderByCreatedAtDesc(User user);
}
