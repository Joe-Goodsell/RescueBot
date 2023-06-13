public class Coordinates {
    private String latitude;
    private String longitude;
    private final String DEFAULT_LATITUDE = "00.0000 N";
    private final String DEFAULT_LONGITUDE = "00.0000 W";

    public Coordinates(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coordinates() {
        this.latitude = DEFAULT_LATITUDE;
        this.longitude = DEFAULT_LONGITUDE;
    }

    public String getLatitude() {
        return this.latitude;
    }
    public String getLongitude() {
        return this.longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public void  setLongitude(String longitude) {
        this.longitude = longitude;
    }
}