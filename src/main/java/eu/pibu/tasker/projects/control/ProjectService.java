package eu.pibu.tasker.projects.control;

import eu.pibu.tasker.Clock;
import eu.pibu.tasker.exceptions.NotFoundException;
import eu.pibu.tasker.projects.boundary.ProjectRepository;
import eu.pibu.tasker.projects.dto.CreateProjectRequest;
import eu.pibu.tasker.projects.dto.UpdateProjectRequest;
import eu.pibu.tasker.projects.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final Clock clock;
    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return StreamSupport.stream(projectRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot find project with id: " + id));
    }
    public Long addProject(String name, String description) {
        return projectRepository.save(new Project(name, description, clock.time())).getId();
    }
    public Long addProject(CreateProjectRequest dto) {
        return projectRepository.save(new Project(dto.getName(), dto.getDescription(), clock.time())).getId();
    }
    public void updateProject(Long id, UpdateProjectRequest dto) {
        Project project = getProjectById(id);
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        projectRepository.save(project);
    }
    public void deleteProjectById(Long id) {
        projectRepository.delete(getProjectById(id));
    }
}