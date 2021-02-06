package org.eguenichon.testcontainers.junit5.container;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Shared Postgres Container across all the unit test by overriding stop() method to let the JVM shut down the Container
 */
public class SharedPostgresSQLContainer extends PostgreSQLContainer<SharedPostgresSQLContainer> {

    private static SharedPostgresSQLContainer container;

    private SharedPostgresSQLContainer() {
        super();
    }

    public static SharedPostgresSQLContainer getInstance() {
        if (container == null) {
            container = new SharedPostgresSQLContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
