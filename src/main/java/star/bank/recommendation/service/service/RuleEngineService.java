package star.bank.recommendation.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import star.bank.recommendation.service.model.RuleCondition;
import star.bank.recommendation.service.repositories.CompositeRecommendationsRepository;
import star.bank.recommendation.service.repositories.RecommendationsRepository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

@Service
public class RuleEngineService {

    private static final Logger logger = LoggerFactory.getLogger(RuleEngineService.class);

    private final RecommendationsRepository repository;
    private final CompositeRecommendationsRepository compositeRepository;
    private final ObjectMapper objectMapper;

    public RuleEngineService(RecommendationsRepository repository, CompositeRecommendationsRepository compositeRepository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.compositeRepository = compositeRepository;
        this.objectMapper = objectMapper;
    }

    public boolean isUserMatchesRule(UUID userId, String ruleJson) {
        try {
            List<RuleCondition> conditions = objectMapper.readValue(ruleJson, new TypeReference<List<RuleCondition>>() {});
            for (RuleCondition condition : conditions) {
                boolean result = checkSingleCondition(userId, condition);
                if (!result) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("RuleEngineService: Ошибка при парсинге или выполнении правила");
            throw new RuntimeException("Ошибка при парсинге правила: " + ruleJson, e);
        }
    }

    private boolean checkSingleCondition(UUID userId, RuleCondition condition) {
        String query = condition.getQuery();
        List<String> args = condition.getArguments();
        boolean negate = condition.isNegate();
        boolean result;

        switch (query) {
            case "USER_OF":
                result = repository.hasTransactionsOfType(userId, args.get(0));
                break;
            case "ACTIVE_USER_OF":
                result = compositeRepository.isActiveUserOf(userId, args.get(0));
                break;
            case "TRANSACTION_SUM_COMPARE":
                result = compositeRepository.compareTransactionSum(userId, args.get(0), args.get(1), args.get(2), Integer.parseInt(args.get(3)));
                break;
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW":
                result = compositeRepository.compareDepositAndWithdraw(userId, args.get(0), args.get(1));
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип запроса " + query);
        }
        return negate ? !result : result;
    }
}
