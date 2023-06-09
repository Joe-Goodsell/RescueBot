import java.util.Optional;

public class Utils {

    public static Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(ext -> ext.contains("."))
                .map(ext -> ext.substring(filename.lastIndexOf(".")+1));
    }
}
