package eu.pibu.tasker.tasks.control;

import eu.pibu.tasker.Clock;
import eu.pibu.tasker.exceptions.NotFoundException;
import eu.pibu.tasker.tasks.boundary.TaskRepository;
import eu.pibu.tasker.tasks.dto.CreateTaskRequest;
import eu.pibu.tasker.tasks.dto.UpdateTaskRequest;
import eu.pibu.tasker.tasks.entity.Task;
import eu.pibu.tasker.tasks.events.TaskDeleted;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final Clock clock;
    private final TaskRepository taskRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public List<Task> getAllTasks() {
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot find task with id: " + id));
    }
    public Long addTask(String title, String description) {
        return taskRepository.save(new Task(title, description, clock.time())).getId();
    }
    public Long addTask(CreateTaskRequest dto) {
        return taskRepository.save(new Task(dto.getTitle(), dto.getDescription(), clock.time())).getId();
    }
    public void updateTask(Long id, UpdateTaskRequest dto) {
        Task task = getTaskById(id);
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        taskRepository.save(task);
    }
    public void deleteTaskById(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
        applicationEventPublisher.publishEvent(new TaskDeleted(task));
    }
    public void save(Task task) {
        taskRepository.save(task);
    }
}