import java.util.ArrayList;

public class Scenario {

    enum Disaster{
        FLOOD, BUSHFIRE, CYCLONE
    }



    public final Disaster DEFAULT_DISASTER = Disaster.BUSHFIRE;
    private Disaster disaster;
    private ArrayList<Location> locations;
    private boolean isBlank;


    public Scenario() {
        this.isBlank = false;
        this.locations = new ArrayList<Location>();
    }

    public Scenario(boolean asBlank) {
        this.isBlank = asBlank;
    }
    public Scenario(Disaster disaster, ArrayList<Location> locations) {
        this();
        this.disaster = disaster;
        this.locations = locations;
    }

    public Scenario(Scenario that) {
        this(that.getDisasterType(), that.getLocations());
    }

    /*
    GETTERS AND SETTERS
     */
    public boolean isBlank() {
        return isBlank;
    }
    public Scenario copy() {
        return new Scenario(this);
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

