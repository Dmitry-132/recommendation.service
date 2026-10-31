package star.bank.recommendation.service.rule;

import star.bank.recommendation.service.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<Recommendation> getRecommendation(UUID userId);
}
