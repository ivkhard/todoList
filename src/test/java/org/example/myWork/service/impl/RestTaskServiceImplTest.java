package org.example.myWork.service.impl;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import org.example.myWork.dto.TaskDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(initializers = {RestTaskServiceImplTest.Initializer.class})
@Testcontainers
public class RestTaskServiceImplTest {

    static final String USER = "user22";
    static final String PASSWORD = "user22";

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13")
            .withDatabaseName("postgres")
            .withUsername("bsc")
            .withPassword("secret");


    @RegisterExtension
    static WireMockExtension wm = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("myWork.ext.url", wm::baseUrl);
        registry.add("myWork.ext.user", () -> USER);
        registry.add("myWork.ext.password", () -> PASSWORD);
    }

    @Autowired
    RestTaskServiceImpl restTaskService;

    @Test
    void findById(WireMockRuntimeInfo wireMockRuntimeInfo) {
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.register(WireMock.get("/task")
                .willReturn(okJson("{\"tasks\": [{\"taskId\": \"3\",\"description\": \"Task3\",\"closed\": false}]}")));

        Optional<TaskDto> taskDto = restTaskService.findById("EXT-3");
        assertEquals("EXT-3", taskDto.get().getId());

    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "myWork.ext.url=http://localhost:8090",
                    "myWork.ext.user=user22",
                    "myWork.ext.password=user22"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
