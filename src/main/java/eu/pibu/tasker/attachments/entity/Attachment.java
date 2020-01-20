package eu.pibu.tasker.attachments.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "uuid")
public class Attachment {
    @Id
    private Long id;
    private String uuid;
    private String filename;
    private LocalDateTime createdAt;

    public Attachment(String filename, LocalDateTime createdAt) {
        this.uuid = UUID.randomUUID().toString();
        this.filename = filename;
        this.createdAt = createdAt;
    }
}