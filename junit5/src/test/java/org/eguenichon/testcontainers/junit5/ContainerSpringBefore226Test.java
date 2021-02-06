package org.eguenichon.testcontainers.junit5;

import org.eguenichon.testcontainers.junit5.container.SharedPostgresSQLContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = ContainerSpringBefore226Test.class)
@ContextConfiguration(initializers = {ContainerSpringBefore226Test.Initializer.class})
@EnableAutoConfiguration
public class ContainerSpringBefore226Test {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = SharedPostgresSQLContainer.getInstance()
            .withDatabaseName("dbname")
            .withUsername("user")
            .withPassword("password");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),

                    "spring.liquibase.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.liquibase.user=" + postgreSQLContainer.getUsername(),
                    "spring.liquibase.password=" + postgreSQLContainer.getPassword(),
                    "spring.liquibase.change-log=classpath:/db/changelog/db.changelog-master.xml"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private DataSource dataSource;

    @Test
    public void testData() throws SQLException {
        // All spring context here is ok, liquibase ran, datasource is ok

        ResultSet result = dataSource.getConnection().createStatement()
                .executeQuery("select * from USER_TABLE");

        assertThat(result.next()).isTrue();
        assertThat(result.getInt("id")).isEqualTo(1);
        assertThat(result.getString("name")).isEqualTo("John Rambo");
    }

}
