package star.bank.recommendation.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition
        (
                info = @Info(
                        title = "Recommendation Service API",
                        version = "1.0"
                )
        )
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
