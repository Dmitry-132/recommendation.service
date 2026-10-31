package star.bank.recommendation.service.model;

import java.util.List;
import java.util.UUID;

public class RecommendationResponse {
    private UUID userId;
    private List<Recommendation> recommendations;

    public RecommendationResponse(UUID userId, List<Recommendation> recommendations) {
        this.userId = userId;
        this.recommendations = recommendations;
    }

    public UUID getUserId() {
        return userId;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }
}
