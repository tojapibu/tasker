package eu.pibu.tasker.projects.control;

import eu.pibu.tasker.Clock;
import eu.pibu.tasker.projects.boundary.ProjectRepository;
import eu.pibu.tasker.projects.boundary.dto.CreateProjectRequest;
import eu.pibu.tasker.projects.boundary.dto.UpdateProjectRequest;
import eu.pibu.tasker.projects.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final Clock clock;
    private final AtomicLong idGenerator = new AtomicLong(1L);
    private final ProjectRepository projectRepository;

    public List<Project> getAll() {
        return projectRepository.findAll();
    }
    public Project getById(Long id) {
        return projectRepository.getById(id);
    }
    public Long add(String title, String description) {
        Long id = idGenerator.getAndIncrement();
        projectRepository.create(new Project(id, title, description, clock.time()));
        return id;
    }
    public Long add(CreateProjectRequest dto) {
        Long id = idGenerator.getAndIncrement();
        projectRepository.create(new Project(id, dto.getTitle(), dto.getDescription(), clock.time()));
        return id;
    }
    public void update(Long id, UpdateProjectRequest dto) {
        Project project = getById(id);
        project.setName(dto.getTitle());
        project.setDescription(dto.getDescription());
    }
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
}