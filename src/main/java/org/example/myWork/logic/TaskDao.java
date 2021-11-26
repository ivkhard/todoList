package org.example.myWork.logic;

import org.example.myWork.logic.TaskInt;
import org.example.myWork.model.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TaskDao implements TaskInt {
    private static final List<Task> storage = new ArrayList<>();
    private long counter = 0;

    @Override
    public void save(Task task) {
        counter++;
        task.setId((int) counter);
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
        for (Iterator<Task> i = storage.listIterator(); i.hasNext(); ) {
            Task t = i.next();
            if (t.getId() == id) {
                i.remove();
                break;
            }
        }
    }
}

