package star.bank.recommendation.service.model;

import java.util.UUID;

public record UserInfo(UUID id, String fullName) { //record автоматически берет дефолтные методы (вызов без get). его поля неизменяемы. можно добавлять свою логику и методы, если они не меняют состояние
}
