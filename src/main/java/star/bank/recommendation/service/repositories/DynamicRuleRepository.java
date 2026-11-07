package star.bank.recommendation.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import star.bank.recommendation.service.model.DynamicRule;

import java.util.UUID;

public interface DynamicRuleRepository extends JpaRepository<DynamicRule, UUID> {
}
