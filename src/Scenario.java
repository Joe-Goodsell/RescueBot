import java.util.ArrayList;

public class Scenario {

    enum Disaster{
        FLOOD, BUSHFIRE, CYCLONE
    }
    enum Legality {
        TRESPASSING, LEGAL
    }


    public final Disaster DEFAULT_DISASTER = Disaster.BUSHFIRE;
    public final Legality DEFAULT_LEGALITY = Legality.LEGAL;
    private ArrayList<Character> characters = new ArrayList<>();

    private Location location;
    private Disaster disaster;
    private Legality legality;

    public Scenario() {

    }
    public Scenario(Disaster disaster, Legality legality, Location location) {
        this.disaster = disaster;
        this.legality = legality;
        this.location = location;
    }
    public Scenario(Disaster disaster, Legality legality, Location location, ArrayList<Character> characters) {
        this(disaster, legality, location);
        this.characters = characters;
    }

    public Scenario(Scenario that) {
        this(that.getDisasterType(), that.getLegality(), that.getLocation(),that.getCharacters());
    }
    public Disaster getDisasterType() {
        return this.disaster;
    }
    public Legality getLegality() {
        return this.legality;
    }
    public ArrayList<Character> getCharacters() {
        return this.characters;
    }
    public Location getLocation() {
        return this.location;
    }
    public void setDisasterType(Disaster disaster) {
        this.disaster = disaster;
    }
    public void setDisasterType() {
        this.disaster = DEFAULT_DISASTER;
    }
    public void setLegality(Legality legality) { this.legality = legality; }
    public void setLegality() { this.legality = DEFAULT_LEGALITY; }
    public void setLocation() {
        this.location = new Location();
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public void addCharacter(Character character) {
    }

    public int getNumCharacters() {
      return characters.size();
    }
}
