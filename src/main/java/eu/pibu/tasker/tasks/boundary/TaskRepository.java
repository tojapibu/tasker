package eu.pibu.tasker.tasks.boundary;

import eu.pibu.tasker.tasks.entity.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();
    Task getById(Long id);
    void create(Task task);
    void deleteById(Long id);
}