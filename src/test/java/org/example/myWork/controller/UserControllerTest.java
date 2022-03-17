package org.example.myWork.controller;

import org.example.myWork.config.SecurityConfig;
import org.example.myWork.logic.UserDao;
import org.example.myWork.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class UserControllerTest {

    static final String PASSWORD = "password";
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private User user;
    private UserDao userDao;
    private UserController userController;

    @BeforeEach
    void setUp() throws Exception {
        user = createTestUser();
        userDao = mock(UserDao.class);
        userController = new UserController(userDao, passwordEncoder);
        when(userDao.save(eq(user))).thenReturn(createTestUser());
    }

    @Test
    public void setRole() {
        userController.createUser(user);
        assertEquals(user.getRole(), SecurityConfig.USER_ROLE);
    }

    @Test
    public void encryptPassword() {
        userController.createUser(user);
        assertNotEquals(PASSWORD, user.getPassword());
    }

    @Test
    public void saveUser() {
        userController.createUser(user);
        verify(userDao).save(user);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void clearPassword() {
        User newUser = userController.createUser(user);
        assertNotEquals(user.getPassword(), newUser.getPassword());
    }

    private static User createTestUser() {
        User testUser = new User();
        testUser.setUsername("User");
        testUser.setPassword(PASSWORD);
        return testUser;
    }
}
