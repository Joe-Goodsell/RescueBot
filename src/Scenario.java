import java.util.*;

public class Scenario {

    enum Disaster{
        FLOOD, BUSHFIRE, CYCLONE, EARTHQUAKE
    }

    private Optional<Integer> userChoice;
    private Optional<Integer> algoChoice;

    private double avgAge;
    private int avgAgeWeight;

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
        this.algoStatistics = new HashMap<>();
        this.locations = new ArrayList<>();
        this.userChoice = Optional.empty();
        this.algoChoice = Optional.empty();
    }

    public Scenario(Disaster disaster, ArrayList<Location> locations) {
        this();
        this.disaster = disaster;
        this.locations = locations;
    }

    public Scenario(Scenario that) {
        this(that.getDisasterType(), that.getLocations());
        this.userChoice = that.getUserChoice();
        this.algoChoice = that.getAlgoChoice();
        this.userStatistics = that.userStatistics;
        this.algoStatistics = that.algoStatistics;
    }

    public Scenario(boolean isRandom) {
        this();
        Random rand = new Random();
        Object[] disasterArr = Arrays.stream(Disaster.values()).toArray();
        this.disaster = (Disaster) disasterArr[rand.nextInt(disasterArr.length)];
        for (int i = 0; i < rand.nextInt(2, 5); i++) {
           locations.add(new Location(true));
        }
    }
    /*
    METHODS
     */

    public String toLogString() {
        String optUserChoice = (userChoice.isPresent()) ? Integer.toString(userChoice.get()) : "null";
        String optAlgoChoice = (algoChoice.isPresent()) ? Integer.toString(algoChoice.get()) : "null";
        return String.format("scenario:%s;USER_CHOICE:%s;ALGO_CHOICE:%s,,,,,,,", disaster.toString().toLowerCase(), optUserChoice, optAlgoChoice);
    }

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

    public void updateStatistics() throws NoSuchElementException {
        updateStatisticsHelper(true);
        updateStatisticsHelper(false);
    }
    public void updateUserStatistics() throws NoSuchElementException {
        updateStatisticsHelper(true);
    }
    public void updateAlgoStatistics() throws NoSuchElementException {
        updateStatisticsHelper(false);
    }
    private void updateStatisticsHelper(boolean isUser) throws NoSuchElementException {
        HashMap<String, Statistic> hm = (isUser) ? userStatistics : algoStatistics;

        int ageTotal = 0;
        int counter = 0;
        for (int i=0; i < locations.size(); i++) {
            Location location = locations.get(i);

            boolean isChoice;
            try {
                int choice = (isUser) ? userChoice.get() : algoChoice.get();
                isChoice = (choice == i+1);
            } catch (NoSuchElementException e) {
                isChoice = false;
            }
            boolean trespassing = location.getLegality().toString().equalsIgnoreCase("trespassing");
            ArrayList<Character> characters = location.getCharacters();

            for (Character character : characters) {
                if (trespassing) {
                    hm.put("trespassing",
                            statIncrementer(isChoice, hm.getOrDefault("trespassing", new Statistic())));
                }

                hm.put(character.getGender().toString(),
                        statIncrementer(isChoice, hm.getOrDefault(character.getGender().toString(), new Statistic())));
                hm.put(character.getBodyType().toString(),
                        statIncrementer(isChoice, hm.getOrDefault(character.getBodyType().toString(), new Statistic())));

                if (character instanceof Animal) {
                    hm.put("animal",
                            statIncrementer(  isChoice, hm.getOrDefault("animal", new Statistic())));
                    if (((Animal) character).getIsPet()) {
                        hm.put("pet",
                                statIncrementer(  isChoice, hm.getOrDefault("pet", new Statistic())));
                    }
                    hm.put(((Animal) character).getSpecies().toString(),
                            statIncrementer(  isChoice, hm.getOrDefault(((Animal) character).getSpecies().toString(), new Statistic())));
                } else if (character instanceof Person) {
                    hm.put("human",
                            statIncrementer(  isChoice, hm.getOrDefault("human", new Statistic())));
                    hm.put(((Person) character).getAgeCategory().toString(),
                            statIncrementer(  isChoice, hm.getOrDefault(((Person) character).getAgeCategory().toString(), new Statistic())));
                    hm.put(((Person) character).getProfession().toString(),
                            statIncrementer(  isChoice, hm.getOrDefault(((Person) character).getProfession().toString(), new Statistic())));
                    if (((Person) character).getIsPregnant()) {
                        hm.put("pregnant",
                                statIncrementer(  isChoice, hm.getOrDefault("pregnant", new Statistic())));
                    }
                    ageTotal = ageTotal + character.getAge();
                    counter++;
                }
            }
        }
        avgAge = ((double) ageTotal) / counter;
        avgAgeWeight = counter;
    }

    // choices are indexed from 1!
    public void setUserChoice(int choice) throws InvalidInputException {
        this.userChoice = Optional.of(choice);
    }

    public void setUserChoice(Optional<Integer> choice) throws InvalidInputException {
        if (choice.isPresent()) {
            this.userChoice = choice;
        } else throw new InvalidInputException();
    }

    public void setAlgoChoice(int choice) throws InvalidInputException {
        this.algoChoice = Optional.of(choice);
    }
    public void setAlgoChoice(Optional<Integer> choice) throws InvalidInputException {
        if (choice.isPresent()) {
            this.algoChoice = choice;
        } else throw new InvalidInputException();
    }

    public Optional<Integer> getUserChoice() {
        return this.userChoice;
    }
    public Optional<Integer> getAlgoChoice() {
        return this.algoChoice;
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

    public double getAvgAge() {
        return this.avgAge;
    }

    public int getAvgAgeWeight() {
        return this.avgAgeWeight;
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

