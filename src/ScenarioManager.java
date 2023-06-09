import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class ScenarioManager {

    protected class InvalidDataFormatException extends Exception {
        public InvalidDataFormatException() {
            super();
        }
    }

    protected class NumberFormatException extends Exception {
        public NumberFormatException() {
            super();
        }
    }

    protected class InvalidCharacteristicException extends Exception {
        public InvalidCharacteristicException() {
            super();
        }
    }

    private ArrayList<Scenario> scenarios = new ArrayList<Scenario>();
    private List<String> warnings = new ArrayList<String>();

    public ScenarioManager() {
        // initialise default ScenarioManager without file

    }


    public ScenarioManager(String path) {
        File file;
        FileHandler fileHandler = new FileHandler();
        try {
            file = fileHandler.checkFile(path);
        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        parseCSV(file);
    }

    public ArrayList<Scenario> getScenarios() {
        return scenarios;
    }

    public void parseCSV(File file) {
        try {
            List<String> values = new ArrayList<String>();
            Scanner parser = new Scanner(file);
            parser.nextLine();
            int lineNumber = 0;
            while (parser.hasNextLine()) {
                ++lineNumber;
                try {
                    readLine(parser.nextLine());
                    parseLine()
                } catch (InvalidDataFormatException e) {
                    String warning = String.format("WARNING: invalid data format in scenarios file in line %d", lineNumber);
                    warnings.add(warning);
                } catch (NumberFormatException e) {
                    String warning = String.format("WARNING: invalid number format in scenarios file in line %d", lineNumber);
                    warnings.add(warning);
                } catch (InvalidCharacteristicException e) {
                    String warning = String.format("WARNING: invalid characteristic in scenarios file in line %d", lineNumber);
                    warnings.add(warning);
                }
                parser.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("failed to read scenarios file");
            System.exit(1);
        }
    }

    private void readLine(String line) throws InvalidDataFormatException {
        // read line from scenarios.csv to an ArrayList
        List<String> values = new ArrayList<String>();
        int counter = 0;
        try (Scanner rowParser = new Scanner(line)) {
            rowParser.useDelimiter(",");
            while (rowParser.hasNext()) {
                counter++;
                if (counter > 7) {
                    throw new InvalidDataFormatException();
                }
                values.add(rowParser.next());
            }
            if (counter != 7) {
                throw new InvalidDataFormatException();
            }
        }
        // parse line, adding new Scenario or new Characters to existing Scenario
        // TODO: line parser

    }

    public List<String> getWarnings() {
        return warnings;
    }
}
