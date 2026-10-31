package star.bank.recommendation.service.service;

import org.springframework.stereotype.Service;
import star.bank.recommendation.service.model.Recommendation;
import star.bank.recommendation.service.repositories.RecommendationsRepository;
import star.bank.recommendation.service.rule.RecommendationRuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> ruleSets;

    public RecommendationService(List<RecommendationRuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }

    public List<Recommendation> getRecommendationsForUser(UUID userId) {
        return ruleSets.stream()
                .map(rule -> rule.getRecommendation(userId))
                .filter(Optional::isPresent)     // уберет пустышки
                .map(Optional::get)              // достает Recommendation из Optional
                .collect(Collectors.toList());
    }
}

