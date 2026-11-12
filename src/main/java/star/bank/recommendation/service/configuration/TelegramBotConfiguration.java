package star.bank.recommendation.service.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfiguration {

    @Value("${telegram.bot.token}")
    private String token;

    @Bean
    @ConditionalOnProperty(name = "telegram.bot.enabled", havingValue = "true", matchIfMissing = false)     //Создаст бин, ТОЛЬКО если telegram.bot.enabled=true. то-же в TelegramBotUpdatesListener
    public TelegramBot telegramBot() {
        return new TelegramBot(token);
    }
}