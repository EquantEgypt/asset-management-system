package org.orange.oie.internship2025.assetmanagementsystem.utils;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;

public class MariadbTestContainers extends MariaDBContainer<MariadbTestContainers> {

    private static final String IMAGE_VERSION = "mariadb:10.6";
    private static MariadbTestContainers container;

    private MariadbTestContainers() {
        super(IMAGE_VERSION);
        withUsername("test");
        withPassword("test");
        withDatabaseName("ams");
        withCommand("--skip-name-resolve");
        withInitScript("init.sql");

    }

    public static MariadbTestContainers getInstance() {
        if (container == null) {
            container = new MariadbTestContainers();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        // Optional: system properties can be set if needed
        System.setProperty("spring.datasource.url", getJdbcUrl());
        System.setProperty("spring.datasource.username", getUsername());
        System.setProperty("spring.datasource.password", getPassword());
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        MariadbTestContainers mariadb = getInstance();
        registry.add("spring.datasource.url", mariadb::getJdbcUrl);
        registry.add("spring.datasource.username", mariadb::getUsername);
        registry.add("spring.datasource.password", mariadb::getPassword);

        // Ensure Flyway / Hibernate use the container DB
        registry.add("spring.flyway.enabled", () -> true);
        registry.add("spring.flyway.schemas", mariadb::getDatabaseName);
        registry.add("spring.jpa.properties.hibernate.default_schema", mariadb::getDatabaseName);
    }

    @Override
    public void stop() {
        // Do nothing, let JVM handle shutdown
    }
}
