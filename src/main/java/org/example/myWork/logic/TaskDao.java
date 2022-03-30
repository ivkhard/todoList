package org.example.myWork.logic;

import org.example.myWork.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TaskDao extends CrudRepository<Task, Integer>, CustomTaskDao {
}
