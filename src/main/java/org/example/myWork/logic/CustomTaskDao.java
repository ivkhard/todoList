package org.example.myWork.logic;

import org.example.myWork.model.Task;
import org.example.myWork.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomTaskDao {
    List<Task> findAllFiltered(String query, boolean excludeCompleted);
}
