package org.example.myWork.logic;

import org.example.myWork.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskDao extends CrudRepository<Task, Integer>, CustomTaskDao {
}
