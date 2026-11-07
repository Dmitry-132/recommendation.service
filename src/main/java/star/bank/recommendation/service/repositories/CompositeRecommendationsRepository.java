package star.bank.recommendation.service.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CompositeRecommendationsRepository { // без Spring Data JPA тело метода нужно писать самому
    private final JdbcTemplate jdbcTemplate;
    private final RecommendationsRepository recommendationsRepository;

    public CompositeRecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate, RecommendationsRepository recommendationsRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.recommendationsRepository = recommendationsRepository;
    }

//Если метод с @Cacheable вызывается изнутри другого метода того же класса, кеш не сработает.
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
