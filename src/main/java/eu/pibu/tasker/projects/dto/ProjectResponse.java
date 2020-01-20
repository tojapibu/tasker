package eu.pibu.tasker.projects.dto;

import eu.pibu.tasker.projects.entity.Project;
import eu.pibu.tasker.tasks.dto.TaskResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProjectResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final List<TaskResponse> tasks;

    public ProjectResponse(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.createdAt = project.getCreatedAt();
        this.tasks = project.getTasks().stream().map(TaskResponse::new).collect(Collectors.toList());
    }
}