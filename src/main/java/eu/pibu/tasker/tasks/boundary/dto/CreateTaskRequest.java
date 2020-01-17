package eu.pibu.tasker.tasks.boundary.dto;

import lombok.Data;

@Data
public class CreateTaskRequest {
    private final String title;
    private final String description;
}