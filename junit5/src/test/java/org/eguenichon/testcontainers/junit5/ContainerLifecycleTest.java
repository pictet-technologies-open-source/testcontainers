package org.eguenichon.testcontainers.junit5;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class ContainerLifecycleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerLifecycleTest.class);

    private static final int INTERNAL_MARIO_PORT = 8080;

    @Container
    private GenericContainer marioContainer = new GenericContainer(DockerImageName.parse("pengbai/docker-supermario"))
            .withLogConsumer(new Slf4jLogConsumer(LOGGER))
            .withExposedPorts(INTERNAL_MARIO_PORT);

    @Test
    public void testLifecycle1() {
        // Nothing we just want to check the logs
    }

    @Test
    public void testLifecycle2() {
        // Nothing we just want to check the logs
    }
}
