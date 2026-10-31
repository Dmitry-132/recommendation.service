package star.bank.recommendation.service.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RecommendationsRepository { // без Spring Data JPA тело метода нужно писать самому
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class, userId);
        return result != null ? result : 0;
    }

    public int getTotalAmountOfTransactions(UUID userId) {
        Integer result = jdbcTemplate.queryForObject("SELECT SUM(amount) FROM transactions WHERE user_id = ?",
                Integer.class, userId);
        return result != null ? result : 0;
    }

    public int getSumTransactionsByType(UUID userId, String productType) {
        String sql = """ 
                SELECT SUM(t.amount)
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ?
                """;
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userId, productType);
        return result != null ? result : 0;
    }

    public int getSumByProductTypeAndTransactionType(UUID userId, String productType, String transactionType) {
        String sql = """
                SELECT SUM(t.amount)
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ?
                  AND p.type = ?
                  AND t.type = ?
                """;
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userId, productType, transactionType);
        return result != null ? result : 0;
    }

    public int getCountTransactionsByType(UUID userId, String productType) {
        String sql = """
                SELECT COUNT(*)
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ?
                """;
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userId, productType);
        return result != null ? result : 0;
    }

    public boolean hasTransactionsOfType(UUID userId, String productType) {
        String sql = """
                SELECT t.id
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ?
                LIMIT 1
                """;
        List<String> ids = jdbcTemplate.queryForList(sql, String.class, userId, productType);
        return !ids.isEmpty();
    }

}
