package eu.pibu.tasker.projects.dto;

import lombok.Data;

@Data
public class UpdateProjectRequest {
    private final String name;
    private final String description;
}
