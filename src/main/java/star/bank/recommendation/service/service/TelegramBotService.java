package star.bank.recommendation.service.service;

import org.springframework.stereotype.Service;
import star.bank.recommendation.service.model.Recommendation;
import star.bank.recommendation.service.model.UserInfo;
import star.bank.recommendation.service.repositories.RecommendationsRepository;

import java.util.List;

@Service
public class TelegramBotService {

    private final RecommendationsRepository repository;
    private final RecommendationService recommendationService;

    public TelegramBotService(RecommendationsRepository repository, RecommendationService recommendationService) {
        this.repository = repository;
        this.recommendationService = recommendationService;
    }

    public String getRecommendationsMessage(String username) {
        UserInfo user = repository.findUserByUsername(username);
        if (user == null) return "Пользователь не найден";
        List<Recommendation> recommendations = recommendationService.getRecommendationsForUser(user.id());

        StringBuilder response = new StringBuilder();
        response.append("Здравствуйте, ").append(user.fullName()).append("!\n\n");

        if (recommendations.isEmpty()) {
            response.append("Новых продуктов для вас нет.");
        } else {
            response.append("Новые продукты для вас:\n\n");
            for (Recommendation rec : recommendations) {
                response.append("   ").append(rec.name())
                        .append(": ").append(rec.description()).append("\n");
            }
        }
        return response.toString();
    }
}
