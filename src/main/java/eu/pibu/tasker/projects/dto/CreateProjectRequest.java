package eu.pibu.tasker.projects.dto;

import lombok.Data;

@Data
public class CreateProjectRequest {
    private final String name;
    private final String description;
}