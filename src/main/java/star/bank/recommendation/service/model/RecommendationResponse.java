package star.bank.recommendation.service.model;

import java.util.List;
import java.util.UUID;

public record RecommendationResponse(UUID userId, List<Recommendation> recommendations) {
}
