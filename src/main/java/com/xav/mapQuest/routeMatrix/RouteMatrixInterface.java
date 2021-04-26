package com.xav.mapQuest.routeMatrix;

import com.xav.pojo.Location;

import java.io.IOException;
import java.util.List;

public interface RouteMatrixInterface {

    public double [][] getAllToAllDistanceMatrix(List<Location> locations) throws IOException;
    public double [][] getAllToAllTimeMatrix(List<Location> locations) throws IOException;

}
