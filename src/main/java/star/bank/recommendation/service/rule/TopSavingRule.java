package star.bank.recommendation.service.rule;

import org.springframework.stereotype.Component;
import star.bank.recommendation.service.model.Recommendation;
import star.bank.recommendation.service.repositories.RecommendationsRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class TopSavingRule implements RecommendationRuleSet{

    private final RecommendationsRepository repository;

    public TopSavingRule(RecommendationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {
        if (!repository.hasTransactionsOfType(userId, "DEBIT")) {
            return Optional.empty();
        }
        int debitDeposits = repository.getSumByProductTypeAndTransactionType(userId, "DEBIT", "DEPOSIT");
        if (debitDeposits < 50_000) {
            if (repository.getSumByProductTypeAndTransactionType(userId, "SAVING", "DEPOSIT") < 50_000) {
                return Optional.empty();
            }
        }
        if (debitDeposits <= repository.getSumByProductTypeAndTransactionType(userId, "DEBIT", "WITHDRAW")) {
            return Optional.empty();
        }
        return Optional.of(new Recommendation(
                UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                "Top Saving",
                """
                        Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!
                        Преимущества «Копилки»:
                        Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.
                        Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.
                        Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.
                        Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!
                        """
        ));
    }

}
