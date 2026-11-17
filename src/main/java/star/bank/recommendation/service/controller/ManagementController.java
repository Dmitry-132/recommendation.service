package star.bank.recommendation.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/management")
@Tag(name = "Мониторинг версии приложения")
public class ManagementController {

    private final BuildProperties buildProperties;
    public ManagementController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @GetMapping("/info")
    @Operation(summary = "Получить информацию о сервисе")
    @ApiResponse(responseCode = "200", description = "Данные сервиса получены (название и версия")
    public ResponseEntity<Map<String, String>> getInfo() {
        Map<String, String> response = Map.of("name", buildProperties.getName(), "version", buildProperties.getVersion());
        return ResponseEntity.ok(response);
    }
}