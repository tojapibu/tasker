package eu.pibu.tasker.attachments.dto;

import lombok.Data;
import org.springframework.core.io.Resource;

@Data
public class DownloadResponse {
    private final String filename;
    private final Resource resource;
}