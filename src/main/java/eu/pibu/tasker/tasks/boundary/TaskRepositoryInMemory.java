package eu.pibu.tasker.tasks.boundary;

import eu.pibu.tasker.exceptions.NotFoundException;
import eu.pibu.tasker.tasks.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskRepositoryInMemory implements TaskRepository {
    private final Set<Task> tasks = new HashSet<>();
    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }
    @Override
    public Task getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Task with id: " + id + " not found"));
    }
    @Override
    public void create(Task task) {
        tasks.add(task);
    }
    @Override
    public void deleteById(Long id) {
        Task task = findById(id).orElseThrow(() -> new NotFoundException("Task with id: " + id + " not found"));
        tasks.remove(task);
    }
    public Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }
}