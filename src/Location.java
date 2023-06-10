public class Location {
    private String latitude;
    private String longitude;
    private final String DEFAULT_LATITUDE = "00.0000 N";
    private final String DEFAULT_LONGITUDE = "00.0000 W";

    public Location(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location() {
        this.latitude = DEFAULT_LATITUDE;
        this.longitude = DEFAULT_LONGITUDE;
    }

    public void setLatitute(String latitude) {
        this.latitude = latitude;
    }
    public void  setLongitude(String longitude) {
        this.longitude = longitude;
    }
}