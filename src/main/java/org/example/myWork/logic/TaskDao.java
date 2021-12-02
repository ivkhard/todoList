package org.example.myWork.logic;

import org.example.myWork.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TaskDao implements ITaskDao {
    private static final List<Task> storage = new ArrayList<>();
    private int counter = 0;

    @Override
    public void save(Task task) {
        counter++;
        task.setId(counter);
        storage.add(task);
    }

    @Override
    public Optional<Task> get(long id) {
        return storage.stream().filter(t -> t.getId() == id).findAny();
    }

    @Override
    public Stream<Task> find(String query, boolean excludeCompleted) {
        Stream<Task> result = storage.stream();
        if (excludeCompleted) {
            result = result.filter(task -> !task.isDone());
        }
        if (query != null && !query.isEmpty()) {
            result = result.filter(task -> task.getDescription().contains(query));
        }
        return result;
    }

    @Override
    public void delete(int id) {
        storage.removeIf(task -> task.getId() == id);
    }
}


