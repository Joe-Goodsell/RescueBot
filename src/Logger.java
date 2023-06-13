import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Logger {

    private static final String DEFAULT_NAME = "RescueBot.log";
    private static final Path DEFAULT_PATH = Paths.get("/Users/josephgoodsell/IdeaProjects/RescueBot/src");
    private Path path;

    public Logger(String candidate) {
        /*
        Construct Logger instance with user-defined path to logfile.
        Reverts to `DEFAULT_PATH` is path is invalid.
         */
        try {
            Path candidatePath = Paths.get(candidate);
            checkPath(candidatePath);
            this.path = candidatePath;
        } catch (FileNotFoundException e) {
            Path path1 = Paths.get(DEFAULT_PATH.toString(), DEFAULT_NAME);
            System.err.printf("WARNING: target directory does not exist or cannot be written to. Setting default path `%s`%n", path1);
            this.path = path1;
        }
    }

    public Logger(Path path) {
        try {
            checkPath(path);
            this.path = path;
        } catch (FileNotFoundException e) {
            Path path1 = Paths.get(DEFAULT_PATH.toString(), DEFAULT_NAME);
            System.err.printf("WARNING: target directory does not exist or cannot be written to. Setting default path `%s`%n", path1);
            this.path = path1;
        }
    }

    public Logger() {
        /*
        Construct Logger instance with `DEFAULT_PATH`
         */
        this.path = Paths.get(DEFAULT_PATH.toString(), DEFAULT_NAME);
    }

    public void setPath(Path candidate) {
        try {
            checkPath(candidate);
            this.path = candidate;
        } catch (FileNotFoundException e) {
            Path path1 = Paths.get(DEFAULT_PATH.toString(), DEFAULT_NAME);
            System.err.printf("WARNING: target directory does not exist or cannot be written to. Setting default path `%s`%n", path1);
            this.path = path1;
        }
    }

    private void checkPath(Path candidate) throws FileNotFoundException {
        /*
        Checks if a path is valid:
            1. if path is a directory, check if we can create new file of `DEFAULT_NAME`;
            2. if path is a file, check if we can create and write to that file.
        This will create a new file if none exists.
         */
        Path path;
        File file = candidate.toFile();
        if (file.isDirectory()) {
           path = Paths.get(String.valueOf(candidate), DEFAULT_NAME);
        } else {
           path = candidate;
        }
        try (FileOutputStream dummy = new FileOutputStream(path.toFile(), true)) { }
        catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

    public void writeLog(ScenarioManager scenarioManager) {
        /*
        Given a `ScenarioManager` instance, write all Scenario, Location (including user and algorithm choices), and Character information to file.
        Path to write logfile already specified.
        */
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(path.toFile()));
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: could not print results. Target directory does not exist.");
            System.err.printf("######## Attempted directory was: %s ########%n", path.toString());
            System.exit(1);
        }
        for (Scenario scenario : scenarioManager.getScenarios()) {
            outputStream.println(scenario.toLogString());
            for (Location location : scenario.getLocations()) {
               outputStream.println(location.toLogString());
               for (Character character : location.getCharacters()) {
                    outputStream.println(character.toLogString());
               }
            }
        }
        outputStream.close();
    }

    public ScenarioManager readLog() throws FileNotFoundException {
        /*
        Returns new `ScenarioManager` instance containing all information in logfile
        Re-uses ScenarioManager's `parseCSV` method
         */
        File file = path.toFile();
        if (file.length() == 0) {
            throw new FileNotFoundException();
        }
        ScenarioManager scenarioManager = new ScenarioManager();
        scenarioManager.parseCSV(file, true);
        return scenarioManager;
    }
}
