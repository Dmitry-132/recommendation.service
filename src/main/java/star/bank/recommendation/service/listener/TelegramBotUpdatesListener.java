package star.bank.recommendation.service.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import star.bank.recommendation.service.service.TelegramBotService;

@Service
@ConditionalOnProperty(name = "telegram.bot.enabled", havingValue = "true", matchIfMissing = false)
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;
    private final TelegramBotService telegramBotService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, TelegramBotService telegramBotService) {
        this.telegramBot = telegramBot;
        this.telegramBotService = telegramBotService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(java.util.List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (update.message() != null && update.message().text() != null) {
                String messageText = update.message().text();
                long chatId = update.message().chat().id();

                if (messageText.equals("/start")) {
                    telegramBot.execute(new SendMessage(chatId,
                            "Бот банка «Стар».\n\nНапиши: /recommend <username>,\nЧтобы получить персональные рекомендации"));
                    return;
                }
                if (messageText.startsWith("/recommend")) {
                    String[] parts = messageText.split(" ");
                    if (parts.length != 2) {
                        telegramBot.execute(new SendMessage(chatId,
                                "Неверный username.\nУбедитесь, что не вводите лишние пробелы\nПример: /recommend sheron.berge"));
                        return;
                    }
                    String response = telegramBotService.getRecommendationsMessage(parts[1]);
                    telegramBot.execute(new SendMessage(chatId, response));
                    return;
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}