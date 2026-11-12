package star.bank.recommendation.service.service;

import org.springframework.stereotype.Service;
import star.bank.recommendation.service.model.DynamicRule;
import star.bank.recommendation.service.model.Recommendation;
import star.bank.recommendation.service.repositories.DynamicRuleRepository;
import star.bank.recommendation.service.rule.RecommendationRuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> ruleSets;
    private final DynamicRuleRepository dynamicRuleRepository;
    private final RuleEngineService ruleEngineService;

    public RecommendationService(List<RecommendationRuleSet> ruleSets, DynamicRuleRepository dynamicRuleRepository, RuleEngineService ruleEngineService) {
        this.ruleSets = ruleSets;
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.ruleEngineService = ruleEngineService;
    }

    public List<Recommendation> getRecommendationsForUser(UUID userId) {
        List<Recommendation> recommendations =
                new ArrayList<>();
//                ruleSets.stream()
//                .map(rule -> rule.getRecommendation(userId))
//                .filter(Optional::isPresent)     // уберет пустышки
//                .map(Optional::get)              // достает Recommendation из Optional
//                .collect(Collectors.toList());

        List<DynamicRule> dynamicRules = dynamicRuleRepository.findAll();
        for (DynamicRule dynamicRule : dynamicRules) {
            if (ruleEngineService.isUserMatchesRule(userId, dynamicRule.getRuleCondition())) {
                dynamicRuleRepository.incrementHitCount(dynamicRule.getId());
                recommendations.add(new Recommendation(
                        dynamicRule.getId(),
                        dynamicRule.getName(),
                        dynamicRule.getDescription()
                ));
            }
        }
        return recommendations;
    }

}
