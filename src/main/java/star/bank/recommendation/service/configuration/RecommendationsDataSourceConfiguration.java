package star.bank.recommendation.service.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class RecommendationsDataSourceConfiguration {

    @Bean(name = "recommendationsDataSource") //DataSource стандартный интерфейс Java. В нём нет методов для выполнения SQL (типа SELECT), он отвечает только за одно: выдавать соединения (Connection) с базой данных.
    public DataSource recommendationsDataSource(@Value("${application.recommendations-db.url}") String recommendationsUrl) {
        var dataSource = new HikariDataSource(); //самый быстрый пул соединений в Java (Spring Boot использует его по умолчанию)
        dataSource.setJdbcUrl(recommendationsUrl); //выбор соединения (URL из properties)
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setReadOnly(true); //все соединения будут выдаваться только для чтения
        return dataSource;
    }
    @Bean(name = "recommendationsJdbcTemplate")
    public JdbcTemplate recommendationsJdbcTemplate( //JdbcTemplate — это удобная обёртка DataSource
            @Qualifier("recommendationsDataSource") DataSource dataSource //без @Qualifier, Spring не знал бы, какой именно DataSource
    ) {
        return new JdbcTemplate(dataSource);
    }
}
//connection pool HikariCP — это дефолтный connection pool, который используется в Spring Boot.
//Он сам устанавливает соединение с базой данных и поддерживает эти соединения.