package star.bank.recommendation.service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bank.recommendation.service.model.DynamicRule;
import star.bank.recommendation.service.model.DynamicRuleDto;
import star.bank.recommendation.service.repositories.DynamicRuleRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DynamicRuleService {
    private final DynamicRuleRepository repository;

    public DynamicRuleService(DynamicRuleRepository repository) {
        this.repository = repository;
    }

    public Map<String, Long> getRulesStats() {
        return repository.findAll().stream().collect(Collectors.toMap(
                rule -> rule.getId().toString(),
                rule -> rule.getHitCount()));
    }

    @Transactional
    public DynamicRule addRule(DynamicRuleDto dto) {
        return saveRule(dto, false);
    }

    @Transactional
    public DynamicRule updateRule(DynamicRuleDto dto) {
        return saveRule(dto, true);
    }

    private DynamicRule saveRule(DynamicRuleDto dto, boolean isUpdate) {
        DynamicRule rule = new DynamicRule();
        if (dto.id() == null) {
            if (isUpdate) {
                throw new IllegalArgumentException("updateRule: Отсутствует UUID");
            }
            rule.setId(UUID.randomUUID());
        } else {
            try {
                rule.setId(UUID.fromString(dto.id()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("saveRule: Неверный формат UUID " + dto.id());
            }
        }
        if (isUpdate && !repository.existsById(rule.getId())) {
            throw new IllegalArgumentException("Правило с ID " + rule.getId() + " не найдено. Используйте POST для создания.");
        }
        if (!isUpdate && repository.existsById(rule.getId())) {
            throw new IllegalArgumentException("Правило с ID " + rule.getId() + " уже существует. Используйте PUT для обновления.");
        }
        rule.setName(dto.name());
        rule.setDescription(dto.description());
        rule.setRuleCondition(dto.rule());
        return repository.save(rule);
    }

    public void deleteRule(UUID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("deleteRule: id не найден");
        }
        repository.deleteById(id);
    }

    public List<DynamicRule> getAllRules() {
        return repository.findAll();
    }
}