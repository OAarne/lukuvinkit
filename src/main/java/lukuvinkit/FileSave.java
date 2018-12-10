package lukuvinkit;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileSave {
    public static Storage loadStorage(String filename) throws IOException {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            return new Storage();
        } else {
            return Storage.fromJSON(new String(Files.readAllBytes(path), Charset.forName("utf-8")));
        }
    }

    public static void saveStorage(String filename, Storage storage) throws IOException {
        Path path = Paths.get(filename);
        String json = storage.toJSON();
        Files.write(path, json.getBytes(Charset.forName("utf-8")), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}