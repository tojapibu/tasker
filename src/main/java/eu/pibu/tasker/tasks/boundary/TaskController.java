package eu.pibu.tasker.tasks.boundary;

import eu.pibu.tasker.tasks.control.TaskService;
import eu.pibu.tasker.tasks.dto.CreateTaskRequest;
import eu.pibu.tasker.tasks.dto.TaskResponse;
import eu.pibu.tasker.tasks.dto.UpdateTaskRequest;
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
@RequestMapping(path = "/api/tasks")
class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody CreateTaskRequest request) {
        log.info("Create new task");
        Long id = taskService.addTask(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping
    public ResponseEntity<?> getTasks() {
        log.info("List all tasks");
        List<TaskResponse> response = taskService.getAllTasks().stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        log.info("Fetch task with id: {}", id);
        TaskResponse response = new TaskResponse(taskService.getTaskById(id));
        return ResponseEntity.ok(response);
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        log.info("Update task with id: {}", id);
        taskService.updateTask(id, request);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        log.info("Delete task with id: {}", id);
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}