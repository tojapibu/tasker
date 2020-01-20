package eu.pibu.tasker.attachments.dto;

import eu.pibu.tasker.attachments.entity.Attachment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttachmentResponse {
    private final Long id;
    private final String uuid;
    private final String filename;
    private final LocalDateTime createdAt;

    public AttachmentResponse(Attachment attachment) {
        this.id = attachment.getId();
        this.uuid = attachment.getUuid();
        this.filename = attachment.getFilename();
        this.createdAt = attachment.getCreatedAt();
    }
}
