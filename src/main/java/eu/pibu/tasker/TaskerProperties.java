package eu.pibu.tasker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.tasker")
public class TaskerProperties {
    private String message;
    private String storagePath;
}