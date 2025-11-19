package com.zhixun.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);

        // 连接池配置
        dataSource.setPoolName("Campus_Found");
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(5);
        dataSource.setConnectionTimeout(30000);
        dataSource.setIdleTimeout(600000);
        dataSource.setMaxLifetime(1800000);
        dataSource.setConnectionTestQuery("SELECT 1");
        dataSource.setLeakDetectionThreshold(60000);
        dataSource.setValidationTimeout(5000);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setFetchSize(100);
        jdbcTemplate.setQueryTimeout(10);
        return jdbcTemplate;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // 改用JPA
    // @Component
    public class DatabaseInitializer {
        // @Autowired
        JdbcTemplate jdbcTemplate;

        // @PostConstruct
        public void init() {
            try {
                // createUsersTable();
                // createItemsTable();
                // createLostRecordTable();
                // createMatchTable();

            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }

        private void createUsersTable() {
            jdbcTemplate.update("CREATE TABLE IF NOT EXISTS Users ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(100) NOT NULL, "
                    + "phoneNumber VARCHAR(20) NOT NULL UNIQUE, "
                    + "otherContact VARCHAR(100),"
                    + "password VARCHAR(100) NOT NULL, "
                    + "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
                    + ")");
        }

        private void createItemsTable() {
            jdbcTemplate.update("CREATE TABLE IF NOT EXISTS Items ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "finderUserId BIGINT NOT NULL, "
                    + "itemName VARCHAR(200) NOT NULL, "
                    + "description TEXT, "
                    + "category VARCHAR(100), "
                    + "foundLocation VARCHAR(300) NOT NULL, "
                    + "foundTime TIMESTAMP NOT NULL, "
                    + "imageUrl VARCHAR(500), "
                    + "storageLocation VARCHAR(300), "
                    + "status VARCHAR(50) DEFAULT 'FOUND', "
                    + "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, "
                    + "FOREIGN KEY (finderUserId) REFERENCES Users(id)"
                    + ")");
        }

        private void createLostRecordTable() {
            jdbcTemplate.update("CREATE TABLE IF NOT EXISTS LostRecords ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "ownerUserId BIGINT NOT NULL, "
                    + "itemName VARCHAR(200) NOT NULL, "
                    + "description TEXT, "
                    + "category VARCHAR(100), "
                    + "lostLocation VARCHAR(300) NOT NULL, "
                    + "lostTime TIMESTAMP NOT NULL, "
                    + "imageUrl VARCHAR(500), "
                    + "contactPhone VARCHAR(20), "
                    + "reward DECIMAL(10,2), "
                    + "status VARCHAR(50) DEFAULT 'LOST', "
                    + "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, "
                    + "FOREIGN KEY (ownerUserId) REFERENCES Users(id)"
                    + ")");
        }

        private void createMatchTable() {
            jdbcTemplate.update("CREATE TABLE IF NOT EXISTS Matches ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "lostRecordId BIGINT NOT NULL, "
                    + "foundItemId BIGINT NOT NULL, "
                    + "matchScore DECIMAL(5,2) NOT NULL, "
                    + "matchReason TEXT, "
                    + "status VARCHAR(50) DEFAULT 'PENDING', "
                    + "matchedByUserId BIGINT, "
                    + "confirmedAt TIMESTAMP, "
                    + "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, "
                    + "FOREIGN KEY (lostRecordId) REFERENCES LostRecords(id), "
                    + "FOREIGN KEY (foundItemId) REFERENCES Items(id), "
                    + "FOREIGN KEY (matchedByUserId) REFERENCES Users(id), "
                    + "UNIQUE (lostRecordId, foundItemId)"
                    + ")");
        }
    }
}