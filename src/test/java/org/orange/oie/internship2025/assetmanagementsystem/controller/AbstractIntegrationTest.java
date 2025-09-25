package org.orange.oie.internship2025.assetmanagementsystem.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.orange.oie.internship2025.assetmanagementsystem.App;
import org.orange.oie.internship2025.assetmanagementsystem.utils.MariadbTestContainers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
@TestExecutionListeners(mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS, value = {
        WithSecurityContextTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public abstract class AbstractIntegrationTest {

    private static final MariadbTestContainers mariadbContainer = MariadbTestContainers.getInstance();

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        mariadbContainer.start();
        registry.add("spring.datasource.url", mariadbContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mariadbContainer::getUsername);
        registry.add("spring.datasource.password", mariadbContainer::getPassword);
    }
}
