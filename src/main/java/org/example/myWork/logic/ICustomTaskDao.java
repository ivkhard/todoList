package org.example.myWork.logic;

import org.example.myWork.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomTaskDao {
    List<Task> findAllFiltered(String query, boolean excludeCompleted);
}
