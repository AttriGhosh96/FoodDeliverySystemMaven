package com.xav.pojo;

public class Restaurant {

    private Location restaurantLocation;
    private String restaurantId;

    public Location getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(Location restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Restaurant(Location restaurantLocation, String restaurantId) {
        this.restaurantLocation = restaurantLocation;
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;

        Restaurant that = (Restaurant) o;

        if (getRestaurantLocation() != null ? !getRestaurantLocation().equals(that.getRestaurantLocation()) : that.getRestaurantLocation() != null)
            return false;
        return getRestaurantId() != null ? getRestaurantId().equals(that.getRestaurantId()) : that.getRestaurantId() == null;
    }

    @Override
    public int hashCode() {
        int result = getRestaurantLocation() != null ? getRestaurantLocation().hashCode() : 0;
        result = 31 * result + (getRestaurantId() != null ? getRestaurantId().hashCode() : 0);
        return result;
    }
}
