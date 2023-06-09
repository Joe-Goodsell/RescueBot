import java.util.ArrayList;

public class Scenario {

    enum Disaster{
        FLOOD, BUSHFIRE, CYCLONE
    }
    enum Legality {
        TRESPASSING, LEGAL
    }

    private ArrayList<Character> characters = new ArrayList<>();
    private String location;
    private Disaster disaster;
    private Legality legality;
    private int numCharacters = 0;

    public Scenario(Disaster disaster, Legality legality, String location) {
        this.disaster = disaster;
        this.legality = legality;
        this.location = location;
    }

    public void addCharacter(Character character) {
    }

    public int getNumCharacters() {
      return characters.size();
    }
}
