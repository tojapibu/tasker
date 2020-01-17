package eu.pibu.tasker.tasks.boundary;

import eu.pibu.tasker.tasks.boundary.dto.*;
import eu.pibu.tasker.tasks.control.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    @PostMapping(path = "/{id}/attachments")
    public ResponseEntity<?> addAttachment(@PathVariable Long id, @RequestBody MultipartFile file) {
        log.info("Upload file: {} to task with id: {}", file.getOriginalFilename(), id);
        String uuid = taskService.addAttachment(id, file);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(uuid).toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping(path = "/{id}/attachments")
    public ResponseEntity<?> getAttachments(@PathVariable Long id) {
        log.info("List all attachments from task with id: {}", id);
        List<AttachmentResponse> response = taskService.getTaskById(id)
                .getAttachments().stream()
                .map(AttachmentResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    @GetMapping(path = "/{id}/attachments/{uuid}")
    public ResponseEntity<?> getAttachment(@PathVariable Long id, @PathVariable String uuid) {
        log.info("Download attachment: {} from task with id: {}", uuid, id);
        DownloadAttachment response = taskService.getAttachment(id, uuid);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + response.getFilename())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(response.getResource());
    }
}