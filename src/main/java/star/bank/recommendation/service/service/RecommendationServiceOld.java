package star.bank.recommendation.service.service;

import org.springframework.stereotype.Service;
import star.bank.recommendation.service.repositories.RecommendationsRepository;

import java.util.UUID;

@Service
public class RecommendationServiceOld {

    private final RecommendationsRepository repository;

    public RecommendationServiceOld(RecommendationsRepository repository) {
        this.repository = repository;
    }

    public String getSimpleCreditRecommendation(UUID userId) {
        boolean hasCredits = repository.hasTransactionsOfType(userId, "CREDIT");
        if (hasCredits) {
            return "c";
        }
        int debitDeposits = repository.getSumByProductTypeAndTransactionType(userId, "DEBIT", "DEPOSIT");
        int debitWithdrawals = repository.getSumByProductTypeAndTransactionType(userId, "DEBIT", "WITHDRAW");
        if (debitDeposits <= debitWithdrawals) {
            return "debitDeposits " + debitDeposits + "  ||  debitWithdrawals " + debitWithdrawals;
        }
        if (debitWithdrawals <= 100000) {
            return String.valueOf(debitWithdrawals);
        }
        return "Откройте мир выгодных кредитов с нами!";
    }

    public String getTopSavingRecommendation(UUID userId) {
        if (!repository.hasTransactionsOfType(userId, "DEBIT")) {
            return null;
        }
        int debitDeposits = repository.getSumByProductTypeAndTransactionType(userId, "DEBIT", "DEPOSIT");
        if (debitDeposits < 50_000) {
            int savingDeposits = repository.getSumByProductTypeAndTransactionType(userId, "SAVING", "DEPOSIT");
            if (savingDeposits < 50_000) {
                return null;
            }
        }
        int debitWithdrawals = repository.getSumByProductTypeAndTransactionType(userId, "DEBIT", "WITHDRAW");
        if (debitDeposits <= debitWithdrawals) {
            return null;
        }
        return "Откройте свою собственную «Копилку» с нашим банком!";
    }

    public String getInvest500Recommendation(UUID userId) {
        if (!repository.hasTransactionsOfType(userId, "DEBIT")) {
            return null;
        }
        if (repository.hasTransactionsOfType(userId, "INVEST")) {
            return null;
        }
        int savingDeposits = repository.getSumByProductTypeAndTransactionType(userId, "SAVING", "DEPOSIT");
        if (savingDeposits > 1_000) {
            return "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка!";
        }
        return null;
    }

}
