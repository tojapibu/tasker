package eu.pibu.tasker.tasks.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
public class Task {
    private final Set<Attachment> attachments = new HashSet<>();
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;

    public Task(Long id, String title, String description, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }
    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }
    public List<Attachment> getAttachments() {
        return new ArrayList<>(attachments);
    }
}