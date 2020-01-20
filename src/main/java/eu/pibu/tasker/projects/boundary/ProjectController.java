package eu.pibu.tasker.projects.boundary;

import eu.pibu.tasker.projects.control.ProjectService;
import eu.pibu.tasker.projects.dto.CreateProjectRequest;
import eu.pibu.tasker.projects.dto.ProjectResponse;
import eu.pibu.tasker.projects.dto.UpdateProjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/projects")
class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getProjects() {
        log.info("Fetch all projects");
        List<ProjectResponse> response = projectService.getAllProjects().stream()
                .map(ProjectResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProject(@PathVariable Long id) {
        log.info("Fetch project with id: {}", id);
        ProjectResponse response = new ProjectResponse(projectService.getProjectById(id));
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<?> addProject(@RequestBody CreateProjectRequest request) {
        log.info("Create new project");
        Long id = projectService.addProject(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody UpdateProjectRequest request) {
        log.info("Update project with id: {}", id);
        projectService.updateProject(id, request);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        log.info("Delete project with id: {}", id);
        projectService.deleteProjectById(id);
        return ResponseEntity.noContent().build();
    }
}