package eu.pibu.tasker.tasks.control;

import eu.pibu.tasker.TaskerProperties;
import eu.pibu.tasker.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {
    private final TaskerProperties properties;

    @PostConstruct
    @SneakyThrows(IOException.class)
    public void empty() {
        log.info("Removing orphaned files from {}", properties.getStoragePath());
        Files.walk(Paths.get(properties.getStoragePath()))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .filter(item -> !item.getPath().equals(properties.getStoragePath()))
                .filter(item -> !item.getPath().endsWith(".keepme"))
                .forEach(File::delete);
    }
    @SneakyThrows(IOException.class)
    public void write(String uuid, MultipartFile file) {
        Path path = Paths.get(properties.getStoragePath()).resolve(uuid);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    }
    @SneakyThrows(MalformedURLException.class)
    public Resource read(String uuid) {
        Path path = Paths.get(properties.getStoragePath()).resolve(uuid);
        if (!Files.isRegularFile(path)) {
            throw new NotFoundException("File with name: " + uuid + " not found");
        }
        return new UrlResource(path.toUri());
    }
    @SneakyThrows(IOException.class)
    public void remove(String uuid) {
        Path path = Paths.get(properties.getStoragePath()).resolve(uuid);
        Files.deleteIfExists(path);
    }
    public void removeAll(List<String> uuids) {
        uuids.forEach(this::remove);
    }
}