package org.example.myWork.logic;

import org.example.myWork.model.Task;
import org.example.myWork.model.User;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(initializers = {TaskDaoTest.Initializer.class})
@Testcontainers
public class TaskDaoTest {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13")
            .withDatabaseName("postgres")
            .withUsername("bsc")
            .withPassword("secret");

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private User owner;

    @SpyBean
    private EntityManager entityManager;

    @Mock
    private Query<Task> typedQuery;

    @Test
    public void findAllFiltered() {
        final String expectedQuery = "SELECT t FROM Task t WHERE t.owner.id = :ownerId";
        doReturn(typedQuery).when(entityManager).createQuery(anyString(), eq(Task.class));

        taskDao.findAllFiltered("", false, owner);

        verify(entityManager).createQuery(expectedQuery, Task.class);
        verify(typedQuery).setParameter("ownerId", owner.getId());
    }

    @Test
    public void runContainer() {
        assertTrue(postgreSQLContainer.isRunning());
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
