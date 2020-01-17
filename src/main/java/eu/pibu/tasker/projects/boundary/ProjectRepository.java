package eu.pibu.tasker.projects.boundary;

import eu.pibu.tasker.projects.entity.Project;

import java.util.List;

public interface ProjectRepository {
    List<Project> findAll();
    Project getById(Long id);
    void create(Project project);
    void deleteById(Long id);
}