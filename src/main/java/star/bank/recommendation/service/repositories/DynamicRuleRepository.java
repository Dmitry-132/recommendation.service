package star.bank.recommendation.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import star.bank.recommendation.service.model.DynamicRule;

import java.util.UUID;

public interface DynamicRuleRepository extends JpaRepository<DynamicRule, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE DynamicRule r SET r.hitCount = r.hitCount + 1 WHERE r.id = :id")
    void incrementHitCount(@Param("id") UUID id);
}
