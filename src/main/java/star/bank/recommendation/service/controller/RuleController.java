package star.bank.recommendation.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import star.bank.recommendation.service.model.DynamicRule;
import star.bank.recommendation.service.model.DynamicRuleDto;
import star.bank.recommendation.service.service.DynamicRuleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class RuleController {
    private final DynamicRuleService ruleService;

    public RuleController(DynamicRuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping
    public ResponseEntity<List<DynamicRule>> getAllRules() {
        return ResponseEntity.ok(ruleService.getAllRules());
    }

    @PostMapping
    public ResponseEntity<DynamicRule> addRule(@RequestBody DynamicRuleDto dto) {
        DynamicRule addedRule = ruleService.addRule(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRule);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable UUID id) {
        ruleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }

}
