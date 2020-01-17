package eu.pibu.tasker.tasks.boundary.dto;

import eu.pibu.tasker.tasks.entity.Attachment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttachmentResponse {
    private final String uuid;
    private final String filename;
    private final LocalDateTime createdAt;

    public AttachmentResponse(Attachment attachment) {
        this.uuid = attachment.getUuid();
        this.filename = attachment.getFilename();
        this.createdAt = attachment.getCreatedAt();
    }
}
