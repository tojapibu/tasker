package eu.pibu.tasker.tasks.events;

import eu.pibu.tasker.tasks.entity.Task;
import lombok.Data;

@Data
public class TaskDeleted {
    private final Task task;
}