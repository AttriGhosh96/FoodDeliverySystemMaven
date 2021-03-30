package com.xav.pojo;


public class Location {

    public  enum LocationType{
        RESTAURANT,
        CUSTOMER
    }

    private LocationType locationType;
    private Double latitude;
    private Double longitude;

    @Override
    public String toString() {
        return "Location{" +
                "locationType='" + locationType + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public Location(LocationType locationType, Double latitude, Double longitude) {
        this.locationType = locationType;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {





        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location = (Location) o;

        if (getLocationType() != null ? !getLocationType().equals(location.getLocationType()) : location.getLocationType() != null)
            return false;
        if (getLatitude() != null ? !getLatitude().equals(location.getLatitude()) : location.getLatitude() != null)
            return false;
        return getLongitude() != null ? getLongitude().equals(location.getLongitude()) : location.getLongitude() == null;
    }

    @Override
    public int hashCode() {
        int result = getLocationType() != null ? getLocationType().hashCode() : 0;
        result = 31 * result + (getLatitude() != null ? getLatitude().hashCode() : 0);
        result = 31 * result + (getLongitude() != null ? getLongitude().hashCode() : 0);
        return result;
    }
}

