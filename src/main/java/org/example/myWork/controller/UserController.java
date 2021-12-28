package org.example.myWork.controller;

import lombok.RequiredArgsConstructor;
import org.example.myWork.filter.SecurityConfig;
import org.example.myWork.logic.UserDao;
import org.example.myWork.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {
    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser (@RequestBody User user) {
        user.setRole(SecurityConfig.USER_ROLE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userDao.save(user);
        newUser.setPassword("<hidden>");
        return newUser;
    }
}
