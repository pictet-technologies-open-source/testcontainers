package org.eguenichon.testcontainers.junit5;

import org.eguenichon.testcontainers.junit5.container.SharedPostgresSQLContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = ContainerSpringAfter226Test.class)
@EnableAutoConfiguration
public class ContainerSpringAfter226Test {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = SharedPostgresSQLContainer.getInstance()
            .withDatabaseName("dbname")
            .withUsername("user")
            .withPassword("password");

    @DynamicPropertySource
    public static void postgreSQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);

        registry.add("spring.liquibase.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.liquibase.user", postgreSQLContainer::getUsername);
        registry.add("spring.liquibase.password", postgreSQLContainer::getPassword);
        registry.add("spring.liquibase.change-log", () -> "classpath:/db/changelog/db.changelog-master.xml");
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
