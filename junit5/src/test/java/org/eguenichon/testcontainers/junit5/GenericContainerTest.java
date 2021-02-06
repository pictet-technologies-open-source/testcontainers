package org.eguenichon.testcontainers.junit5;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class GenericContainerTest {

    private static final int INTERNAL_MARIO_PORT = 8080;

    @Container
    private GenericContainer marioContainer = new GenericContainer(DockerImageName.parse("pengbai/docker-supermario"))
                .withExposedPorts(INTERNAL_MARIO_PORT);

    @Test
    public void testRunningMario() throws IOException {

        String host = marioContainer.getHost();
        int exposedPort = marioContainer.getMappedPort(INTERNAL_MARIO_PORT);

        URL url = new URL("http://" + host + ":" + exposedPort);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        assertEquals(200, con.getResponseCode());
    }

}
