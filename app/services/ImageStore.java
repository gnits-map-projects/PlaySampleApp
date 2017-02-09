package services;

import play.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ImageStore {

    private static final Path IMAGES_ROOT = Paths.get("/tmp/play/images");

    public ImageStore() {
        File rootDir = IMAGES_ROOT.toFile();
        if (!rootDir.exists() && rootDir.mkdirs()) {
            Logger.error("Failed to create image upload directory");
        }
    }

    public String storeImage(Path source) throws IOException {

        final String imageId = createImageId();
        final Path target = createImagePath(imageId + ".png");

        Logger.debug("source: {} target: {}", source, target);

        Files.move(source, target, REPLACE_EXISTING);
        Logger.debug("Upload file: {}, to path: {}", source, target);

        return imageId;
    }

    public File getImage(String id) {

        final File file = createImagePath(id + ".png").toFile();
        if (!file.isFile()) {
            return null;
        }

        return file;
    }

    public boolean deleteImage(String id) throws IOException {

        final Path path = createImagePath(id + ".png");
        if (!path.toFile().isFile()) {
            return false;
        }

        Files.deleteIfExists(path);
        return true;
    }

    private static Path createImagePath(String imageId) {
        return IMAGES_ROOT.resolve(imageId);
    }

    private static String createImageId() {
        final UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
