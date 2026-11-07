package star.bank.recommendation.service.configuration;

import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class RulesDataSourceConfiguration {

    @Bean
    @Primary // ставти DataSource по умолчанию
//    @ConfigurationProperties("spring.datasource") если будет еще бд, или повторить из recommendations
    public DataSourceProperties rulesDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource rulesDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build(); // берет настройки из application.properties (spring.datasource.*)
    }


}
