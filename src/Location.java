import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Location {

    enum Legality {
        TRESPASSING, LEGAL
    }

    private Coordinates coordinates;
    private Legality legality;

    public final Legality DEFAULT_LEGALITY = Legality.LEGAL;
    private ArrayList<Character> characters;
    private boolean isBlank;


    /*
    CONSTRUCTORS
     */
    public Location() {
        this.characters = new ArrayList<Character>();
    }

    public Location(boolean isRandom) {
        Random rand = new Random();
        this.characters = new ArrayList<>();
        this.coordinates = new Coordinates(true);
        Object[] legalityArr = Arrays.stream(Legality.values()).toArray();
        this.legality = (Legality) legalityArr[rand.nextInt(legalityArr.length)];

        int nCharacters = rand.nextInt(2,9);
        for(int i=0; i < nCharacters; i++) {
            if (Math.random() > 0.5) {
                this.addCharacter(new Animal(true));
            } else {
                this.addCharacter(new Person(true));
            }
        }
    }


    public Location(Coordinates coordinates) {
        this();
        this.coordinates = coordinates;
    }

    public Location(Location that) {
        this.coordinates = that.getCoordinates();
        this.legality = that.getLegality();
        this.characters = that.getCharacters();
    }

    /*
    METHODS
     */
    public String toLogString() {
        return String.format("location:%s;%s;%s,,,,,,,",
                coordinates.getLatitude(),
                coordinates.getLongitude(),
                legality.toString().toLowerCase());
    }
    public Location copy() {
        return new Location(this);
    }



    /*
    GETTERS AND SETTERS
     */
    public boolean isBlank() {
        return isBlank;
    }
    public void addCharacter(Character character) {
        characters.add(character);
    }

    public Legality getLegality() {
        return this.legality;
    }

    public ArrayList<Character> getCharacters() {
        return this.characters;

    }

    public void setLegality(Legality legality) {
        this.legality = legality;
    }

    public void setLegality() {
        this.legality = DEFAULT_LEGALITY;
    }


    public int getNumCharacters() {
        return characters.size();
    }


        public void setCoordinates() { this.coordinates = new Coordinates(); }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
