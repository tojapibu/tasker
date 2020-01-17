package eu.pibu.tasker.projects.entity;

import eu.pibu.tasker.tasks.entity.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
public class Project {
    private final Set<Task> tasks = new HashSet<>();
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public Project(Long id, String name, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }
    public void addTask(Task task) {
        tasks.add(task);
    }
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}