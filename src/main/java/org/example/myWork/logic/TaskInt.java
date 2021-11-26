package org.example.myWork.logic;

import org.example.myWork.model.Task;

import java.util.Optional;
import java.util.stream.Stream;

public interface TaskInt {
    void save(Task task);

    Optional<Task> get(long id);

    Stream<Task> find(String query, boolean excludeCompleted);

    void delete(int id);
}
