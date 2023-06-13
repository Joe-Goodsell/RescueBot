import java.util.ArrayList;
import java.util.HashMap;

public class Scenario {

    enum Disaster{
        FLOOD, BUSHFIRE, CYCLONE
    }

    private int userChoice;
    private int algoChoice;

    private HashMap<String,Statistic> userStatistics;
    private HashMap<String,Statistic> algoStatistics;



    public final Disaster DEFAULT_DISASTER = Disaster.BUSHFIRE;
    private Disaster disaster;
    private ArrayList<Location> locations;
    private boolean isBlank;

    /*
    CONSTRUCTORS
     */
    public Scenario() {
        this.userStatistics = new HashMap<>();
        this.locations = new ArrayList<Location>();
    }

    public Scenario(Disaster disaster, ArrayList<Location> locations) {
        this();
        this.disaster = disaster;
        this.locations = locations;
    }

    public Scenario(Scenario that) {
        this(that.getDisasterType(), that.getLocations());
        this.userStatistics = that.userStatistics;
        this.algoStatistics = that.algoStatistics;
    }
    /*
    METHODS
     */

    public Scenario copy() {
        return new Scenario(this);
    }

    public void ppScenario() {
        System.out.println("======================================");
        System.out.printf("# Scenario: %s%n", this.disaster.toString().toLowerCase());
        System.out.println("======================================");

        for (int i = 0; i < this.locations.size(); i++) {
            Location loc = this.locations.get(i);
            Coordinates coords = loc.getCoordinates();
            System.out.printf("[%d] Location: %s, %s%n", i+1, coords.getLatitude(), coords.getLongitude());
            String isTrespassing = (loc.getLegality().toString().equalsIgnoreCase("trespassing")) ? "yes" : "no";
            ArrayList<Character> characters = loc.getCharacters();
            System.out.printf("%d Characters:%n", characters.size());
            for (int j = 0; j < characters.size(); j++) {
                System.out.printf("- %s%n", characters.get(j).toString());
            }
        }
    }



    /*
    GETTERS AND SETTERS
     */
    public HashMap<String, Statistic> getUserStatistics() {
        return userStatistics;
    }

    public HashMap<String, Statistic> getAlgoStatistics() {
        return algoStatistics;
    }


    private Statistic statIncrementer(boolean isUserChoice, Statistic stat) {
        if (isUserChoice) {
            stat.incrementSaved();
        } else stat.incrementTotal();

        return stat;
    }

    public void updateStatistics(boolean user) {
        HashMap<String, Statistic> hm = (user) ? userStatistics : algoStatistics;

        int ageTotal = 0;
        for (int i=0; i < locations.size(); i++) {
            Location location = locations.get(i);
            boolean isUserChoice = (userChoice == i + 1);
            boolean trespassing = location.getLegality().toString().equalsIgnoreCase("trespassing");
            ArrayList<Character> characters = location.getCharacters();
            int counter = 0;
            for (Character character : characters) {
                if (trespassing) {
                    hm.put("trespassing",
                            statIncrementer(isUserChoice, hm.getOrDefault("trespassing", new Statistic())));
                }

                hm.put(character.getGender().toString(),
                        statIncrementer(isUserChoice, hm.getOrDefault(character.getGender().toString(), new Statistic())));
                hm.put(character.getBodyType().toString(),
                        statIncrementer(isUserChoice, hm.getOrDefault(character.getBodyType().toString(), new Statistic())));

                if (character instanceof Animal) {
                    if (((Animal) character).getIsPet()) {
                        hm.put("pet",
                                statIncrementer(isUserChoice, hm.getOrDefault("pet", new Statistic())));
                    }
                    hm.put(((Animal) character).getSpecies().toString(),
                            statIncrementer(isUserChoice, hm.getOrDefault(((Animal) character).getSpecies().toString(), new Statistic())));
                } else if (character instanceof Person) {
                    hm.put(((Person) character).getAgeCategory().toString(),
                            statIncrementer(isUserChoice, hm.getOrDefault(((Person) character).getAgeCategory().toString(), new Statistic())));
                    hm.put(((Person) character).getProfession().toString(),
                            statIncrementer(isUserChoice, hm.getOrDefault(((Person) character).getProfession().toString(), new Statistic())));
                    if (((Person) character).getIsPregnant()) {
                        hm.put("pregnant",
                                statIncrementer(isUserChoice, hm.getOrDefault("pregnant", new Statistic())));
                    }
                    ageTotal = ageTotal + character.getAge();
                    counter++;
                }
            }
        }
    }

    // choices are indexed from 1!
    public void setUserChoice(int choice) throws InvalidInputException {
        if (choice > 0 && choice <= this.locations.size()+1) {
            this.userChoice = choice;
        } else throw new InvalidInputException();

        updateStatistics(true);
    }

    public void setAlgoChoice(int choice) throws InvalidInputException {
        if (choice > 0 && choice <= this.locations.size()+1) {
            this.algoChoice = choice;
        } else throw new InvalidInputException();

        updateStatistics(false);
    }

    public boolean isBlank() {
        return isBlank;
    }
    public void addLocation(Location location) {
        locations.add(location);
    }
    public Disaster getDisasterType() {
        return this.disaster;
    }

    public ArrayList<Location> getLocations() {
        return this.locations;
    }
    public void setDisasterType(Disaster disaster) {
        this.disaster = disaster;
    }
    public void setDisasterType() {
        this.disaster = DEFAULT_DISASTER;
    }


}

