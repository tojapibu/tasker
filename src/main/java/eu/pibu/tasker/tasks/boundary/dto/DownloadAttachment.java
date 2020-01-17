package eu.pibu.tasker.tasks.boundary.dto;

import lombok.Data;
import org.springframework.core.io.Resource;

@Data
public class DownloadAttachment {
    private final Resource resource;
    private final String filename;
}
