package star.bank.recommendation.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Управление правилами")
public class RuleController {
    private final DynamicRuleService ruleService;
    private final CacheService cacheService;

    public RuleController(DynamicRuleService ruleService, CacheService cacheService) {
        this.ruleService = ruleService;
        this.cacheService = cacheService;
    }

    @GetMapping
    @Operation(summary = "Получить все динамические правила")
    @ApiResponse(responseCode = "200", description = "Список правил получен")
    public ResponseEntity<List<DynamicRule>> getAllRules() {
        return ResponseEntity.ok(ruleService.getAllRules());
    }

    @GetMapping("/stats")
    @Operation(summary = "Получить статистику использования правил")
    @ApiResponse(responseCode = "200", description = "Статистика использования получена")
    public ResponseEntity<Map<String, Map<String, Long>>> getStats() {
        Map<String, Long> stats = ruleService.getRulesStats();
        return ResponseEntity.ok(Map.of("stats", stats));
    }

    @PostMapping
    @Operation(summary = "Добавить правило")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Правило добавлено"),
            @ApiResponse(responseCode = "400", description = "Неверный формат входных данных"),
            @ApiResponse(responseCode = "409", description = "Правило с таким ID уже есть")
    })
    public ResponseEntity<DynamicRule> addRule(@RequestBody DynamicRuleDto dto) {
        DynamicRule addedRule = ruleService.addRule(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRule);
    }

    @PutMapping
    @Operation(summary = "Обновить правило")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Правило обновлено"),
            @ApiResponse(responseCode = "404", description = "Правило с таким ID не найдено")
    })
    public ResponseEntity<DynamicRule> updateRule(@RequestBody DynamicRuleDto dto) {
        DynamicRule updateRule = ruleService.updateRule(dto);
        return ResponseEntity.ok(updateRule);
    }

    @PostMapping("/clear-cache")
    @Operation(summary = "Сбросить кеш", description = "Очищает все кэшированные данные")
    @ApiResponse(responseCode = "200", description = "Кеш очищен")
    public ResponseEntity<String> clearCache() {
        cacheService.clearAllCaches();
        return ResponseEntity.ok("Кеш очищен");
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить правило")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Правило удалено"),
            @ApiResponse(responseCode = "404", description = "Правило с таким ID не найдено")
    })
    public ResponseEntity<Void> deleteRule(@PathVariable UUID id) {
        ruleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }

}
