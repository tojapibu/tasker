package eu.pibu.tasker.tasks.control;

import eu.pibu.tasker.Clock;
import eu.pibu.tasker.exceptions.NotFoundException;
import eu.pibu.tasker.tasks.boundary.TaskRepository;
import eu.pibu.tasker.tasks.boundary.dto.CreateTaskRequest;
import eu.pibu.tasker.tasks.boundary.dto.DownloadAttachment;
import eu.pibu.tasker.tasks.boundary.dto.UpdateTaskRequest;
import eu.pibu.tasker.tasks.entity.Attachment;
import eu.pibu.tasker.tasks.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final Clock clock;
    private final AtomicLong taskIdGenerator = new AtomicLong(1L);
    private final TaskRepository taskRepository;
    private final StorageService storageService;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public Task getTaskById(Long id) {
        return taskRepository.getById(id);
    }
    public Long addTask(String title, String description) {
        Long id = taskIdGenerator.getAndIncrement();
        taskRepository.create(new Task(id, title, description, clock.time()));
        return id;
    }
    public Long addTask(CreateTaskRequest dto) {
        Long id = taskIdGenerator.getAndIncrement();
        taskRepository.create(new Task(id, dto.getTitle(), dto.getDescription(), clock.time()));
        return id;
    }
    public void updateTask(Long id, UpdateTaskRequest dto) {
        Task task = getTaskById(id);
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
    }
    public void deleteTaskById(Long id) {
        storageService.removeAll(getTaskById(id)
                .getAttachments().stream()
                .map(Attachment::getUuid)
                .collect(Collectors.toList()));
        taskRepository.deleteById(id);
    }
    public DownloadAttachment getAttachment(Long taskId, String fileUuid) {
        Attachment attachment = getTaskById(taskId)
                .getAttachments().stream()
                .filter(a -> a.getUuid().equals(fileUuid))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("File with uuid: " + fileUuid + " not found"));
        Resource resource = storageService.read(fileUuid);
        return new DownloadAttachment(resource, attachment.getFilename());
    }
    public String addAttachment(Long taskId, MultipartFile file) {
        Attachment attachment = new Attachment(file.getOriginalFilename(), clock.time());
        storageService.write(attachment.getUuid(), file);
        getTaskById(taskId).addAttachment(attachment);
        return attachment.getUuid();
    }
}