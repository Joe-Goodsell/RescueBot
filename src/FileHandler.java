import java.io.File;
import java.io.FileNotFoundException;

public class FileHandler {

    protected File file;
    protected String filetype = "default";

    public FileHandler() {

    }

    public File checkFile(String path) throws FileNotFoundException {
        this.file = new File(path);
        if (!file.exists()) {
            String msg = "could not find " + filetype + ".";
            throw new java.io.FileNotFoundException(msg);
        }
        return file;
    }

}
