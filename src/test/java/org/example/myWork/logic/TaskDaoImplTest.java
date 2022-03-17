package org.example.myWork.logic;

import org.example.myWork.model.Task;
import org.example.myWork.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class TaskDaoImplTest {

    @InjectMocks
    private TaskDaoImpl taskDao;

    @Mock
    private EntityManager entityManager;

    private static User owner = new User();

    @Mock
    private TypedQuery<Task> typedQuery;

    @BeforeAll
    public static void setUp() {
        owner.setId(1);
    }

    @Test
    void findAllFiltered_WhenUserIsNotNull() {
        final String expectedQuery = "SELECT t FROM Task t WHERE t.owner.id = :ownerId";
        when(entityManager.createQuery(anyString(), eq(Task.class))).thenReturn(typedQuery);

        taskDao.findAllFiltered("", false, owner);

        verify(entityManager).createQuery(expectedQuery, Task.class);
        verify(typedQuery).setParameter("ownerId", owner.getId());
    }

    @Test
    void findAllFiltered_Description() {
        final String expectedQuery = "SELECT t FROM Task t WHERE t.description like :desc AND t.owner.id = :ownerId";
        final String description = "Task";
        when(entityManager.createQuery(anyString(), eq(Task.class))).thenReturn(typedQuery);

        taskDao.findAllFiltered(description, false, owner);

        verify(entityManager).createQuery(expectedQuery, Task.class);
        verify(typedQuery).setParameter("ownerId", owner.getId());
        verify(typedQuery).setParameter("desc", "%" + description + "%");
    }
}