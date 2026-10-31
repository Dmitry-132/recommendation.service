package star.bank.recommendation.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import star.bank.recommendation.service.repositories.RecommendationsRepository;
import star.bank.recommendation.service.service.RecommendationService;
import star.bank.recommendation.service.service.RecommendationServiceOld;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RecommendationServiceTest {

    @Autowired
    private RecommendationServiceOld service;

    @Test
    @DisplayName("Простой кредит")
    void testGetSimpleCreditRecommendation() {
        UUID userUUID = UUID.fromString("1f9b149c-6577-448a-bc94-16bea229b71a"); // подошел и Top Saving
        String recommendation = service.getSimpleCreditRecommendation(userUUID);

        System.out.println(recommendation);
        assertNotNull(recommendation);
        assertTrue(recommendation.startsWith("Откройте мир выгодных кредитов"));
    }
    @Test
    @DisplayName("Top Saving")
    void testGetTopSavingRecommendation() {
        UUID userUUID = UUID.fromString("d4a4d619-9a0c-4fc5-b0cb-76c49409546b");
        String recommendation = service.getTopSavingRecommendation(userUUID);

        System.out.println(recommendation);
        assertNotNull(recommendation);
        assertTrue(recommendation.startsWith("Откройте свою собственную «Копилку»"));
    }

    @Test
    @DisplayName("Invest 500")
    void testGetInvest500Recommendation() {
        UUID userUUID = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");// тоже подошел и к Top Saving
        String recommendation = service.getInvest500Recommendation(userUUID);

        System.out.println(recommendation);
        assertNotNull(recommendation);
        assertTrue(recommendation.startsWith("Откройте свой путь"));
    }

}
