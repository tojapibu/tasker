package eu.pibu.tasker.attachments.boundary;

import eu.pibu.tasker.attachments.control.AttachmentService;
import eu.pibu.tasker.attachments.dto.AttachmentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
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
@RequestMapping(path = "/api/tasks/{taskId}/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping
    public ResponseEntity<?> addAttachment(@PathVariable Long taskId, @RequestBody MultipartFile file) {
        log.info("Upload file: {} to task with id: {}", file.getOriginalFilename(), taskId);
        String uuid = attachmentService.addAttachment(taskId, file);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(uuid).toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping
    public ResponseEntity<?> getAttachments(@PathVariable Long taskId) {
        log.info("List all attachments from task with id: {}", taskId);
        List<AttachmentResponse> response = attachmentService.getAttachments(taskId).stream()
                .map(AttachmentResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    @GetMapping(path = "/{uuid}")
    public ResponseEntity<?> getAttachment(@PathVariable Long taskId, @PathVariable String uuid) {
        log.info("Download attachment: {} from task with id: {}", uuid, taskId);
        Resource response = attachmentService.getAttachment(taskId, uuid);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + response.getFilename())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(response);
    }
}