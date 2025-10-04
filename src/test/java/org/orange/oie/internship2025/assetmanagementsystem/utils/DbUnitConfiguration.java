package org.orange.oie.internship2025.assetmanagementsystem.utils;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DbUnitConfiguration {


    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(System.getProperty("spring.datasource.url"));
        driverManagerDataSource.setUsername(System.getProperty("spring.datasource.username"));
        driverManagerDataSource.setPassword(System.getProperty("spring.datasource.password"));
        return driverManagerDataSource;
    }

    @Bean
    public DatabaseConfigBean databaseConfig() {
        DatabaseConfigBean config = new DatabaseConfigBean();
        config.setDatatypeFactory(new MySqlDataTypeFactory()); // or MariaDbDataTypeFactory if available
        config.setMetadataHandler(new MySqlMetadataHandler()); // 👈 this fixes column case mismatches
        config.setCaseSensitiveTableNames(false); // 👈 makes DbUnit ignore case
        return config;
    }

    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(
            DatabaseConfigBean databaseConfigBean,
            DriverManagerDataSource driverManagerDataSource) {

        DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection = new DatabaseDataSourceConnectionFactoryBean();
        dbUnitDatabaseConnection.setDataSource(driverManagerDataSource);
        dbUnitDatabaseConnection.setDatabaseConfig(databaseConfigBean);
        dbUnitDatabaseConnection.setSchema("ams");
        return dbUnitDatabaseConnection;
    }
}
