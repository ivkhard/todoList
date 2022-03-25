package org.example.myWork.logic;

import org.example.myWork.model.Task;
import org.example.myWork.model.User;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    private UserDao userDao;

    private User owner;

    private Task task1;

    private Task task2;

    @SpyBean
    private EntityManager entityManager;

    @Mock
    private Query<Task> typedQuery;

    public void createUserAndTask() {
        task1 = new Task();
        task1.setDescription("firstTask");

        task2 = new Task();
        task2.setDescription("secondTask");

        owner = new User();
        owner.setId(1);
        owner.setUsername("user");
        owner.setPassword("user");

        userDao.save(owner);

        task1.setOwner(owner);
        taskDao.save(task1);

        task2.setOwner(owner);
        taskDao.save(task2);
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
