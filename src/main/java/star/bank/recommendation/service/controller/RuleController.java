package star.bank.recommendation.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import star.bank.recommendation.service.model.DynamicRule;
import star.bank.recommendation.service.model.DynamicRuleDto;
import star.bank.recommendation.service.service.CacheService;
import star.bank.recommendation.service.service.DynamicRuleService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class RuleController {
    private final DynamicRuleService ruleService;
    private final CacheService cacheService;

    public RuleController(DynamicRuleService ruleService, CacheService cacheService) {
        this.ruleService = ruleService;
        this.cacheService = cacheService;
    }

    @GetMapping
    public ResponseEntity<List<DynamicRule>> getAllRules() {
        return ResponseEntity.ok(ruleService.getAllRules());
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Map<String, Long>>> getStats() {
        Map<String, Long> stats = ruleService.getRulesStats();
        return ResponseEntity.ok(Map.of("stats", stats));
    }

    @PostMapping
    public ResponseEntity<DynamicRule> addRule(@RequestBody DynamicRuleDto dto) {
        DynamicRule addedRule = ruleService.addRule(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRule);
    }

    @PostMapping("/clear-cache")
    public ResponseEntity<String> clearCache() {
        cacheService.clearAllCaches();
        return ResponseEntity.ok("Кеш успешно очищен");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable UUID id) {
        ruleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }

}
