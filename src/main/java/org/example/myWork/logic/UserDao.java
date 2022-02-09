package org.example.myWork.logic;

import org.example.myWork.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDao extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
