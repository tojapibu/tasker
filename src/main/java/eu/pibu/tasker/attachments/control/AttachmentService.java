package eu.pibu.tasker.attachments.control;

import eu.pibu.tasker.Clock;
import eu.pibu.tasker.TaskerProperties;
import eu.pibu.tasker.attachments.entity.Attachment;
import eu.pibu.tasker.exceptions.NotFoundException;
import eu.pibu.tasker.tasks.control.TaskService;
import eu.pibu.tasker.tasks.entity.Task;
import eu.pibu.tasker.tasks.events.TaskDeleted;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final Clock clock;
    private final TaskerProperties properties;
    private final TaskService taskService;

    @PostConstruct
    @SneakyThrows(IOException.class)
    private void empty() {
        log.info("Removing orphaned files from {}", properties.getStoragePath());
        Files.walk(Paths.get(properties.getStoragePath()))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .filter(item -> !item.getPath().equals(properties.getStoragePath()))
                .filter(item -> !item.getPath().endsWith(".keepme"))
                .forEach(File::delete);
    }
    @SneakyThrows(IOException.class)
    private void write(String uuid, MultipartFile file) {
        Path path = Paths.get(properties.getStoragePath()).resolve(uuid);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    }
    @SneakyThrows(MalformedURLException.class)
    private Resource read(String uuid) {
        Path path = Paths.get(properties.getStoragePath()).resolve(uuid);
        if (!Files.isRegularFile(path)) {
            throw new NotFoundException("File with name: " + uuid + " not found");
        }
        return new UrlResource(path.toUri());
    }
    @SneakyThrows(IOException.class)
    private void remove(String uuid) {
        Path path = Paths.get(properties.getStoragePath()).resolve(uuid);
        Files.deleteIfExists(path);
    }
    private void removeAll(List<String> uuids) {
        uuids.forEach(this::remove);
    }
    public String addAttachment(Long taskId, MultipartFile file) {
        Attachment attachment = new Attachment(file.getOriginalFilename(), clock.time());
        Task task = taskService.getTaskById(taskId);
        task.addAttachment(attachment);
        taskService.save(task);
        write(attachment.getUuid(), file);
        return attachment.getUuid();
    }
    public List<Attachment> getAttachments(Long taskId) {
        return taskService.getTaskById(taskId).getAttachments();
    }
    public Resource getAttachment(Long taskId, String fileUuid) {
        Task task = taskService.getTaskById(taskId);
        //TODO check if task and has attachment in set
        Resource resource = read(fileUuid);
        return resource;
    }
    @EventListener
    public void handle(TaskDeleted event) {
        log.info("Receive TaskDeleted event: {}", event);
        removeAll(event.getTask().getAttachments().stream().map(Attachment::getUuid).collect(Collectors.toList()));
    }
}