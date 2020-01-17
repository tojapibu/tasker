package eu.pibu.tasker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(TaskerProperties.class)
public class TaskerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskerApplication.class, args);
    }
    @Bean
    public Clock clock() {
        log.info("Initializing clock");
        return new SystemClock();
    }
}