import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;


public class ScenarioManager {

    protected class InvalidDataFormatException extends Exception {
        public InvalidDataFormatException() {
            super();
        }
    }

    protected class NumberFormatException extends java.lang.NumberFormatException {
        public NumberFormatException() {
            super();
        }
    }

    protected class InvalidCharacteristicException extends Exception {
        public InvalidCharacteristicException(int lineNumber) {
            super();
            String warning = String.format("WARNING: invalid characteristic in scenarios file in line %d", lineNumber);
            warnings.add(warning);
        }
    }

    private ArrayList<Scenario> scenarios = new ArrayList<Scenario>();
    private ArrayList<String> warnings = new ArrayList<String>();

    public ScenarioManager() {
        // initialise default ScenarioManager without file

    }

    public void loadFromFile(File scenarioFile) throws FileNotFoundException {
        try {
            parseCSV(scenarioFile);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }


//    public ScenarioManager(String path) {
//        File file;
//        FileHandler fileHandler = new FileHandler();
//        try {
//            file = fileHandler.checkFile(path);
//        } catch (FileNotFoundException e) {
//            System.exit(1);
//        }
//        try {
//            parseCSV(file);
//        } catch (FileNotFoundException e) {
//            System.exit(1);
//        }
//    }

    public ArrayList<Scenario> getScenarios() {
        return scenarios;
    }

    public void parseCSV(File file) throws FileNotFoundException {
        Scenario currentScenario = null;
        List<String> values;
        int lineNumber = 0;
        Scanner parser;
        parser = new Scanner(file);
        parser.nextLine();

        while (parser.hasNextLine()) {
            lineNumber++;
            currentScenario = parseLine(parser.nextLine(), lineNumber, currentScenario);
        }
    }

    public Scenario parseLine(String line, int lineNumber, Scenario currentScenario) {
        Character currentCharacter = null;
        String[] elements = line.split(",");

        Arrays.asList(elements).forEach(e -> System.out.print(e + "|"));
        System.out.println();
        try {
            if (elements.length != 8) {
                throw new InvalidDataFormatException();
            }
        } catch (InvalidDataFormatException e) {
            String warning = String.format("WARNING: invalid data format in scenarios file in line %d", lineNumber);
            warnings.add(warning);
        }

        if (elements[0].contains("scenario")) {

            // create new scenario, saving old one if it exists
            if (currentScenario != null) {
                scenarios.add(new Scenario(currentScenario));
            }
            currentScenario = new Scenario();

            String disasterTypeString = elements[0].substring(elements[0].indexOf(":") + 1);
            Scenario.Disaster disasterType = Stream.of(Scenario.Disaster.values())
                    .filter(p -> p.toString().equalsIgnoreCase(disasterTypeString))
                    .findFirst()
                    .orElse(null);
            try {
                if (disasterType != null) {
                    currentScenario.setDisasterType(disasterType);
                } else throw new InvalidCharacteristicException(lineNumber);
            } catch (InvalidCharacteristicException e) {
                currentScenario.setDisasterType();
            }
            return currentScenario; // go to next line
        } else if (elements[0].contains("location")) {
            if (currentScenario.getLocation() != null) { // if current scenario already has location data, we need to create a new scenario
                if (currentScenario != null) {
                    scenarios.add(new Scenario(currentScenario));
                }
                currentScenario = new Scenario();
            }
            String[] data = elements[0].substring(elements[0].indexOf(":") + 1).split(";");
            currentScenario.setLocation(new Location(data[0], data[1]));

            Scenario.Legality legality = Stream.of(Scenario.Legality.values())
                    .filter(p -> p.toString().equalsIgnoreCase(data[2]))
                    .findFirst()
                    .orElse(null);
            try {
                if (legality != null) {
                    currentScenario.setLegality(legality);
                } else throw new InvalidCharacteristicException(lineNumber);
            } catch (InvalidCharacteristicException e) {
                currentScenario.setLegality();
                String warning = String.format("WARNING: invalid characteristic in scenarios file in line %d", lineNumber);
                warnings.add(warning);
            }
            return currentScenario; // go to next line
        } else {
            try {
                if (elements[0].equalsIgnoreCase("human")) {
                    currentCharacter = new Person();
                } else if (elements[0].equalsIgnoreCase("animal")) {
                    currentCharacter = new Animal();
                } else {
                    throw new InvalidCharacteristicException(lineNumber);
                }
            } catch (InvalidCharacteristicException e) {
                return currentScenario;
            }
        }

        Character.Gender gender = Stream.of(Character.Gender.values())
                .filter(p -> p.toString().equalsIgnoreCase(elements[1]))
                .findFirst()
                .orElse(null);

        try {
            if (gender != null) {
                currentCharacter.setGender(gender);
            } else throw new InvalidCharacteristicException(lineNumber);
        } catch (InvalidCharacteristicException e) {
            currentCharacter.setGender(); // set default
        }
        try {
            int age = Integer.parseInt(elements[2]);
            if (age > 0) {
                currentCharacter.setAge(age);
            } else throw new InvalidCharacteristicException(lineNumber);
        } catch (NumberFormatException | InvalidCharacteristicException e) {
            currentCharacter.setAge(); // set default value
        }

        Character.BodyType bodyType  = Stream.of(Character.BodyType.values())
                .filter(p -> p.toString().equalsIgnoreCase(elements[3]))
                .findFirst()
                .orElse(null);
        try {
            if (bodyType != null) {
                currentCharacter.setBodyType(bodyType);
            } else throw new InvalidCharacteristicException(lineNumber);
        } catch (InvalidCharacteristicException e) {
            currentCharacter.setBodyType(); // set default
        }

        if (currentCharacter instanceof Person) {
            Person.Profession profession = Stream.of(Person.Profession.values())
                    .filter(p -> p.toString().equalsIgnoreCase(elements[4]))
                    .findFirst()
                    .orElse(null);

            try {
                if (profession != null) {
                    ((Person) currentCharacter).setProfession(profession);
                } else throw new InvalidCharacteristicException(lineNumber);
            } catch (InvalidCharacteristicException e) {
                ((Person) currentCharacter).setProfession();
            }

            ((Person) currentCharacter).setIsPregnant(elements[5].equalsIgnoreCase("true"));

        } else {
            Animal.Species species = Stream.of(Animal.Species.values())
                    .filter(p -> p.toString().equalsIgnoreCase(elements[6]))
                    .findFirst()
                    .orElse(null);
            try {
                if (species != null) {
                    ((Animal) currentCharacter).setSpecies(species);
                } else throw new InvalidCharacteristicException(lineNumber);
            } catch (InvalidCharacteristicException e) {
                currentCharacter.setBodyType(); // set default
            }

            try {
                if (elements[7].equalsIgnoreCase("true")) {
                    ((Animal) currentCharacter).setIsPet(true);
                } else if (elements[7].equalsIgnoreCase("false")) {
                    ((Animal) currentCharacter).setIsPet(false);
                } else throw new InvalidCharacteristicException(lineNumber);
            } catch (InvalidCharacteristicException e) {
                ((Animal) currentCharacter).setIsPet();
            }
        }

        return currentScenario;
    }




    public ArrayList<String> getWarnings() {
        return warnings;
    }

    public int getNumScenarios() {
        return scenarios.size();
    }

}
