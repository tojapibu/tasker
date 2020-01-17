package eu.pibu.tasker.tasks.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "uuid")
public class Attachment {
    private final String uuid = UUID.randomUUID().toString();
    private final String filename;
    private final LocalDateTime createdAt;
}