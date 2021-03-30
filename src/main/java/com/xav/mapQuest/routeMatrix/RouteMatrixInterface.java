package com.xav.mapQuest.routeMatrix;

import com.xav.pojo.Location;

import java.util.List;

public interface RouteMatrixInterface {

    public double [][] getAllToAll(List<Location> locations);

}
