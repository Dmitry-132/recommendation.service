package star.bank.recommendation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import star.bank.recommendation.service.repositories.RecommendationsRepository;

import java.util.UUID;

@SpringBootTest
public class RecommendationsRepositoryTest {

    @Autowired
    private RecommendationsRepository repository; // Внедряем бин

    @Test
    void testGetRandomTransactionAmount() {
        int amount = repository.getRandomTransactionAmount(UUID.fromString("1f9b149c-6577-448a-bc94-16bea229b71a"));

        System.out.println(amount);
    }

    @Test
    void testGetTotalAmountOfTransactions() {
        int amount = repository.getTotalAmountOfTransactions(UUID.fromString("958c44cd-f82c-4d7d-97c5-eb1dc45ce6b5"));

        System.out.println("Сумма: " + amount);
    }

    @Test
    void testGetSumTransactionsByType() {
        int amount = repository.getSumTransactionsByType(UUID.fromString(
                "958c44cd-f82c-4d7d-97c5-eb1dc45ce6b5"),
                "DEBIT");

        System.out.println("Сумма по типу: " + amount);
    }

}
