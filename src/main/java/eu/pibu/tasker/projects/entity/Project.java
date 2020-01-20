package eu.pibu.tasker.projects.entity;

import eu.pibu.tasker.tasks.entity.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
public class Project {
    @Id
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private Set<Task> tasks;

    public Project(String name, String description, LocalDateTime createdAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.tasks = new HashSet<>();
    }
    public void addTask(Task task) {
        tasks.add(task);
    }
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}