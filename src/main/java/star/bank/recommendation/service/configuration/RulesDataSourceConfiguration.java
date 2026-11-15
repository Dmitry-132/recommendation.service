package star.bank.recommendation.service.configuration;

import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class RulesDataSourceConfiguration {

    @Bean
    @Primary
    public DataSourceProperties rulesDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource rulesDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
}
