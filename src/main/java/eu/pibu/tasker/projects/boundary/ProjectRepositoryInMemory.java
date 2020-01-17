package eu.pibu.tasker.projects.boundary;

import eu.pibu.tasker.exceptions.NotFoundException;
import eu.pibu.tasker.projects.entity.Project;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProjectRepositoryInMemory implements ProjectRepository {
    private final Set<Project> projects = new HashSet<>();

    @Override
    public List<Project> findAll() {
        return new ArrayList<>(projects);
    }
    @Override
    public Project getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Project with id: " + id + " not found"));
    }
    @Override
    public void create(Project project) {
        projects.add(project);
    }
    @Override
    public void deleteById(Long id) {
        Project project = findById(id).orElseThrow(() -> new NotFoundException("Project with id: " + id + " not found"));
        projects.remove(project);
    }
    private Optional<Project> findById(Long id) {
        return projects.stream()
                .filter(project -> project.getId().equals(id))
                .findFirst();
    }
}