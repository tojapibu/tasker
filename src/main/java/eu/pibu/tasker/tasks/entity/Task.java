package eu.pibu.tasker.tasks.entity;

import eu.pibu.tasker.attachments.entity.Attachment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
public class Task {
    @Id
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Set<Attachment> attachments;

    public Task(String title, String description, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.attachments = new HashSet<>();
    }
    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }
    public List<Attachment> getAttachments() {
        return new ArrayList<>(attachments);
    }
}