package star.bank.recommendation.service.rule;

import org.springframework.stereotype.Component;
import star.bank.recommendation.service.model.Recommendation;
import star.bank.recommendation.service.repositories.RecommendationsRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCreditRule implements RecommendationRuleSet {

    private final RecommendationsRepository repository;

    public SimpleCreditRule(RecommendationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {
        if (repository.hasTransactionsOfType(userId, "CREDIT")) {
            return Optional.empty();
        }
        int withdrawals = repository.getSumByProductTypeAndTransactionType(userId, "DEBIT", "WITHDRAW");
        if (withdrawals < 100000) {
            return Optional.empty();
        }
        if (withdrawals > repository.getSumByProductTypeAndTransactionType(userId, "DEBIT", "DEPOSIT")) {
            return Optional.empty();

        }
        return Optional.of(new Recommendation(
                UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
                "Простой кредит",
                """
                        Откройте мир выгодных кредитов с нами! 
                        Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.
                        Почему выбирают нас:
                        Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.
                        Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.
                        Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.
                        Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!
                        """
        ));
    }
}
