package star.bank.recommendation.service.service;

import org.springframework.stereotype.Service;
import star.bank.recommendation.service.model.DynamicRule;
import star.bank.recommendation.service.model.DynamicRuleDto;
import star.bank.recommendation.service.repositories.DynamicRuleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DynamicRuleService {
    private final DynamicRuleRepository repository;

    public DynamicRuleService(DynamicRuleRepository repository) {
        this.repository = repository;
    }

    public DynamicRule addRule(DynamicRuleDto dto) {
        DynamicRule rule = new DynamicRule();
        if (dto.getId() == null) {
            rule.setId(UUID.randomUUID());
        } else {
            try {
                rule.setId(UUID.fromString(dto.getId()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("addRule: Неверный формат UUID " + dto.getId());
            }
        }
        rule.setName(dto.getName());
        rule.setDescription(dto.getDescription());
        rule.setRuleCondition(dto.getRule());

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