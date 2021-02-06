package org.eguenichon.testcontainers.junit5;

import org.eguenichon.testcontainers.junit5.container.SharedPostgresSQLContainer;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class ContainerSingletonTest {

    @Container
    private SharedPostgresSQLContainer sharedPostgresSQLContainer = SharedPostgresSQLContainer.getInstance();

    @Test
    public void testLifecycle1() {
        // Nothing we just want to check the logs
    }

    @Test
    public void testLifecycle2() {
        // Nothing we just want to check the logs
    }
}
