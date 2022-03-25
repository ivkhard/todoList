package org.example.myWork.logic;

import org.example.myWork.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserDao extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
