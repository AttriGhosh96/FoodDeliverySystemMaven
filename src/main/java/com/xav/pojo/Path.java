package com.xav.pojo;

import com.xav.GeneratePopulation;
import com.xav.Utility;
import com.xav.receivedData.Order;
import org.decimal4j.util.DoubleRounder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Path {
    private List<Location> pathLocations;
    private List<GapedOrder> pathGapedOrders;
    private OrderMaps pathMaps;

    private double [] cumulativeTimeArray;

    private GeneratePopulation generatePopulation;

    public Path(List<GapedOrder> pathGapedOrders) {
        this.pathGapedOrders = pathGapedOrders;
        generatePathLocation();
        generateOrderMaps();
        generatePopulation = GeneratePopulation.getGeneratePopulationInstance();
        generateCumulativeTimeArray(); //newly added

    }

    public List<Location> getPathLocations() {
        return pathLocations;
    }

    private void setPathLocations(List<Location> pathLocations) {
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


    //for setting the pathLocation
    private void generatePathLocation()
    {
        pathLocations= Utility.gapedOrderListToLocationList(pathGapedOrders);
    }

    //for setting the orderMaps
    private void generateOrderMaps()
    {
        OrderMaps orderMaps;
        Set<Order> orders = getPathGapedOrders().stream().map(gapedOrder -> gapedOrder.getOrder()).collect(Collectors.toSet());
        pathMaps = new OrderMaps();
        pathMaps.updateOrderMaps(orders);
    }

    //function to get the total order value of the path
    public double getTotalOrderValue()
    {
        double totalOrderValue = 0;
        for(int i=0; i<pathGapedOrders.size(); i++)
        {
            totalOrderValue += pathGapedOrders.get(i).getOrder().getOrderValue();
        }

        return totalOrderValue;
    }

    //function to get the total distance of the path
    public double getTotalDistance() throws IOException {

        double totalDistance = 0;

        double [][] allToAllDistanceMatrix = generatePopulation.getAllToAllDistanceMatrix();
        Map<Location, Integer> locationIndexMap = generatePopulation.getLocationIndexMap();


//        //printing allToALLDistanceMatrix
//        for(int i=0; i<allToAllDistanceMatrix.length; i++)
//        {
//            for(int j=0; j<allToAllDistanceMatrix[i].length; j++) {
//                System.out.print(allToAllDistanceMatrix[i][j] + " ");
//
//            }
//            System.out.println();
//        }

        for (int i=0; i<pathLocations.size()-1; i++)
        {
            int locationOneIndex = locationIndexMap.get(pathLocations.get(i));
            int locationTwoIndex = locationIndexMap.get(pathLocations.get(i+1));

//            System.out.println(pathLocations.get(i)+"  ---  "+pathLocations.get(i+1)+"LocationOneIndex " + locationOneIndex + " LocationTwoIndex " +locationTwoIndex + "  Distance "+ allToAll[locationOneIndex][locationTwoIndex]);

            totalDistance += allToAllDistanceMatrix[locationOneIndex][locationTwoIndex];
        }

        return totalDistance;

    }

    //fill the cumulative time array
    public void generateCumulativeTimeArray()
    {

        double [][] allToAllTimeMatrix = generatePopulation.getAllToAllTimeMatrix();
        Map<Location, Integer> locationIndexMap = generatePopulation.getLocationIndexMap();

        cumulativeTimeArray = new double[pathLocations.size()];

        double cumulativeTime = 0;

        // this is the wait time at the first restaurant
        //time in seconds
        cumulativeTimeArray[0] = Utility.MAX_PREPARATION_TIME_AT_RESTAURANT + Utility.MAX_HANDOVER_TIME_AT_RESTAURANT;

        for(int i=1; i<pathLocations.size(); i++)
        {
            int locationOneIndex = locationIndexMap.get(pathLocations.get(i-1));
            int locationTwoIndex = locationIndexMap.get(pathLocations.get(i));

            cumulativeTime = cumulativeTimeArray[i-1] + allToAllTimeMatrix[locationOneIndex][locationTwoIndex];

            if(pathLocations.get(i).getLocationType() == Location.LocationType.RESTAURANT)
            {
                if(cumulativeTime < Utility.MAX_PREPARATION_TIME_AT_RESTAURANT){
                    cumulativeTime += Utility.MAX_PREPARATION_TIME_AT_RESTAURANT - cumulativeTime;
                }
                cumulativeTime+=Utility.MAX_HANDOVER_TIME_AT_RESTAURANT;

            }
            cumulativeTimeArray[i] = cumulativeTime;

        }

    }

    //function to get the total time of the path
    public double getTotalTime() //Path.getTotalTime = the total time to service all orders in the path along with wait time
    {
//        System.out.println("Cumulative time array: ");
//        //printing the cumulative array
//        for(int i=0; i<cumulativeTimeArray.length; i++)
//        {
//            System.out.print(cumulativeTimeArray[i] + " ");
//        }
//        System.out.println();

        return cumulativeTimeArray[cumulativeTimeArray.length-1];
    }


    //function to check pareto
/**
     *
     * @param path
     * @return  0   -   when  neither path dominates
     *          1   -   when calling path dominates path passed as parameter
     *          -1  -   when path passed as parameter dominates the calling path
     *
     */
    public int dominates(Path path) throws IOException {
        int dominanceArray[] = new int[3];

        //totalOrderValue
        dominanceArray[0] = Utility.checkDominanceOfDouble(this.getTotalOrderValue(), path.getTotalOrderValue(), 1);
        //totalDistance
        dominanceArray[1] = Utility.checkDominanceOfDouble(this.getTotalDistance(), path.getTotalDistance(), -1);
        //totalTime
        dominanceArray[2] = Utility.checkDominanceOfDouble(this.getTotalTime(), path.getTotalTime(),-1);

        Arrays.sort(dominanceArray);

        int firstElement = dominanceArray[0];

        int sumOfFirstNLast = dominanceArray[0] + dominanceArray[dominanceArray.length - 1];

        if( firstElement == -1){

            if(sumOfFirstNLast > -1){
                return 0;
            }
            return  -1;

        }else if(firstElement == 0){
            if(sumOfFirstNLast > 0)
            {
                return 1;
            }
            return 0;
        }
        else
            return 1;

    }



    //to print path in desired format
    public void getPathPrintedInRequiredFormat()
    {
        List<Location> alreadyVisitedRestaurant = new ArrayList<Location>();
        for(Location location: pathLocations)
        {
            for(GapedOrder gapedOrder: pathGapedOrders)
            {
                if((gapedOrder.getOrder().getRestaurant().getRestaurantLocation() == location) && !alreadyVisitedRestaurant.contains(location)) {
                    System.out.print("Pick up order from Restaurant " + gapedOrder.getOrder().getRestaurant().getRestaurantId() + " at [" + DoubleRounder.round(location.getLatitude(),3) + " , " + DoubleRounder.round(location.getLongitude(),3) + "]  ");
                    alreadyVisitedRestaurant.add(location);
                }
                else if(gapedOrder.getOrder().getCustomer().getCustomerLocation() == location)
                    System.out.print("Drop off order at Customer " + gapedOrder.getOrder().getCustomer().getCustomerId() + " at [" + DoubleRounder.round(location.getLatitude(),3) + " , " + DoubleRounder.round(location.getLongitude(),3) + "]  ");
            }
        }
    }


}
