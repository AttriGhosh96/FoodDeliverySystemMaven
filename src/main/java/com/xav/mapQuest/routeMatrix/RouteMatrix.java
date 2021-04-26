package com.xav.mapQuest.routeMatrix;

import com.xav.pojo.Location;

import java.io.IOException;
import java.util.List;

public class RouteMatrix implements RouteMatrixInterface {

    @Override
    public double[][] getAllToAllDistanceMatrix(List<Location> locations)throws IOException {
//        double[][] allToAll =
//                {{0, 1.115, 1.255, 1.42, 1.367, 2.036, 2.82},
//                {0.859, 0, 1.622, 0.667, 1.703, 2.608, 2.066},
//                {1.519, 1.903, 0, 1.973, 1.816, 1.152, 3.247},
//                {0.91, 1.123, 2.054, 0, 2.263, 2.323, 2.039},
//                {1.455, 1.268, 1.795, 1.573, 0, 2.874, 3.212},
//                {2.266, 2.458, 1.457, 2.27, 2.809, 0, 3.543},
//                {1.451, 1.009, 2.425, 0.438, 2.149, 2.694, 0}};
        PostRequestCheck setConnToGetAllToAllDistanceMatrix = new PostRequestCheck();
        double [][] allToAllDistanceMatrix = setConnToGetAllToAllDistanceMatrix.getAllToAllDistances(locations);

        return allToAllDistanceMatrix;


    }

    @Override
    public double[][] getAllToAllTimeMatrix(List<Location> locations) throws IOException {
        PostRequestCheck setConnToGetAllToAllTimeMatrix = new PostRequestCheck();
        double [][] allToAllTimeMatrix = setConnToGetAllToAllTimeMatrix.getAllToAllTimeMatrix(locations);

        return allToAllTimeMatrix;
    }
}
