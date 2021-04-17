package com.xav.pojo;

import com.xav.Utility;

import java.util.List;

public class Path {
    private List<Location> pathLocations;
    private List<GapedOrder> pathGapedOrders;
    private OrderMaps pathMaps;


    public Path(List<Location> pathLocations) {
        this.pathLocations = pathLocations;
    }

    public List<Location> getPathLocations() {
        return pathLocations;
    }

    public void setPathLocations(List<Location> pathLocations) {
        this.pathLocations = pathLocations;
    }

    public List<GapedOrder> getPathGapedOrders() {
        return pathGapedOrders;
    }

    public void setPathGapedOrders(List<GapedOrder> pathGapedOrders) {
        this.pathGapedOrders = pathGapedOrders;
    }

    public OrderMaps getPathMaps() {
        return pathMaps;
    }

    public void setPathMaps(OrderMaps pathMaps) {
        this.pathMaps = pathMaps;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;

        Path that = (Path) o;

        return pathLocations != null ? pathLocations.equals(that.pathLocations) : that.pathLocations == null;
    }

    @Override
    public int hashCode() {
        return pathLocations != null ? pathLocations.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ResultPath{" +
                "path=" + pathLocations +
                '}';
    }


    //to set Path
    public void setPath()
    {
        //list of location is set
        setPathLocations(Utility.gapedOrderListToLocationList(pathGapedOrders));



    }
}
