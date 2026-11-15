package star.bank.recommendation.service.repositories;

import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CompositeRecommendationsRepository {
    private final RecommendationsRepository recommendationsRepository;

    public CompositeRecommendationsRepository(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public boolean isActiveUserOf(UUID userId, String productType) {
        int count = recommendationsRepository.getCountTransactionsByType(userId, productType);
        return count >= 5;
    }

    public boolean compareDepositAndWithdraw(UUID userId, String productType, String operator) {
        int deposits = recommendationsRepository.getSumByProductTypeAndTransactionType(userId, productType, "DEPOSIT");
        int withdrawals = recommendationsRepository.getSumByProductTypeAndTransactionType(userId, productType, "WITHDRAW");

        return compare(deposits, withdrawals, operator);
    }

    public boolean compareTransactionSum(UUID userId, String productType, String transactionType, String operator, int value) {
        int sum = recommendationsRepository.getSumByProductTypeAndTransactionType(userId, productType, transactionType);
        return compare(sum, value, operator);
    }

    private boolean compare(int left, int right, String operator) {
        return switch (operator) {
            case ">" -> left > right;
            case "<" -> left < right;
            case "=" -> left == right;
            case ">=" -> left >= right;
            case "<=" -> left <= right;
            case "!=" -> left != right;
            default -> throw new IllegalArgumentException("compare: неизвестный оператор " + operator);
        };
    }

}
