package eu.pibu.tasker.projects.boundary;

import eu.pibu.tasker.projects.entity.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
}