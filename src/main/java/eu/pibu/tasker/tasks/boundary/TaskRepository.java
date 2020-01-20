package eu.pibu.tasker.tasks.boundary;

import eu.pibu.tasker.tasks.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}