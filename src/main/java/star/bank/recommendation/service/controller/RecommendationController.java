package star.bank.recommendation.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import star.bank.recommendation.service.model.Recommendation;
import star.bank.recommendation.service.model.RecommendationResponse;
import star.bank.recommendation.service.service.RecommendationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
@Tag(name = "Рекомендации")
public class RecommendationController {
    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @GetMapping("/{user_id}")
    @Operation(summary = "Получить рекомендации для выбранного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список рекомендаций получен"),
            @ApiResponse(responseCode = "400", description = "Неверный формат UUID пользователя")
    })
    public ResponseEntity<RecommendationResponse> getRecommendations(@PathVariable("user_id") UUID userId) {
        List<Recommendation> recommendations = service.getRecommendationsForUser(userId);
        RecommendationResponse response = new RecommendationResponse(userId, recommendations);
        return ResponseEntity.ok(response);
    }
}
