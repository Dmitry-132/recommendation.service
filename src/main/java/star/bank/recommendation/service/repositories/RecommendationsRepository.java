package star.bank.recommendation.service.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import star.bank.recommendation.service.model.UserInfo;

import java.util.List;
import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

    @Cacheable(value = "getSumByProductTypeAndTransactionTypeCache", key = "#userId + '_' + #productType + '_' + #transactionType")
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

    @Cacheable(value = "getCountTransactionsByTypeCache", key = "#userId + '_' + #productType")
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

    @Cacheable(value = "hasTransactionsOfTypeCache", key = "#userId + '_' + #productType")
    public boolean hasTransactionsOfType(UUID userId, String productType) {
        String sql = """
                SELECT 1
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ?
                LIMIT 1
                """;
        List<String> ids = jdbcTemplate.queryForList(sql, String.class, userId, productType);
        return !ids.isEmpty();
    }

    public UserInfo findUserByUsername(String username) {
        String sql = "SELECT id, first_name, last_name FROM users WHERE username = ?";
        List<UserInfo> users = jdbcTemplate.query(sql, (rs, rowNum) -> new UserInfo(
                UUID.fromString(rs.getString("id")),
                rs.getString("first_name") + " " +
                        rs.getString("last_name")), username);
        if (users.size() == 1) {
            return users.get(0);
        }
        return null;
    }
}
//rs (ResultSet) — это объект, в котором лежит текущая строка из базы данных. У него есть свои методы
//rowNum — это номер текущей строки (начиная с 0). здесь он не нужен, но RowMapper требует его в качестве второго параметра (такова сигнатура интерфейса)
//можно заменить "first_name" на индекс столбца