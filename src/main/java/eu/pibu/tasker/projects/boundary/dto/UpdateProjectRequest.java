package eu.pibu.tasker.projects.boundary.dto;

import lombok.Data;

@Data
public class UpdateProjectRequest {
    private final String title;
    private final String description;
}
