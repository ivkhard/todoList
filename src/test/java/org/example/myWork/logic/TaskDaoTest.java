package org.example.myWork.logic;

import org.example.myWork.model.Task;
import org.example.myWork.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(initializers = {TaskDaoTest.Initializer.class})
@Testcontainers
@Transactional
public class TaskDaoTest {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13")
            .withDatabaseName("postgres")
            .withUsername("bsc")
            .withPassword("secret");

    @Autowired
    private TaskDao taskDao;

    private User owner;

    private Task task1;

    private Task task2;

    @SpyBean
    private EntityManager entityManager;

    public void createUserAndTask() {
        task1 = new Task();
        task1.setDescription("firstTask");

        task2 = new Task();
        task2.setDescription("secondTask");

        owner = new User();
        owner.setUsername("user");
        owner.setPassword("user");

        entityManager.persist(owner);

        task1.setOwner(owner);
        entityManager.persist(task1);

        task2.setOwner(owner);
        entityManager.persist(task2);
    }

    @Test
    public void findAllFiltered() {

        createUserAndTask();

        List<Task> taskList = taskDao.findAllFiltered("Task", false, owner);
        List<Task> expectedList = new ArrayList<>();
        expectedList.add(task1);
        expectedList.add(task2);

        assertTrue(taskList.containsAll(expectedList));
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
