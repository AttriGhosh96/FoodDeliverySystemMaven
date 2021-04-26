package com.xav;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.xav.pojo.*;
import com.xav.receivedData.Order;

import java.io.IOException;
import java.util.*;

public class Utility {


    public static final double MAX_PREPARATION_TIME_AT_RESTAURANT = 10.0 * 60; // seconds
    public static final double MAX_HANDOVER_TIME_AT_RESTAURANT = 2.0 * 60; // seconds

    //to get the distance matrixs
    public static double[][] extractSubArray(double[][] array, int startIndexRow, int endIndexRow, int startIndexCol, int endIndexCol)
    {
        double[][] extractedSubArray = new double[(endIndexRow-startIndexRow)+1][(endIndexCol-startIndexCol)+1];
        int i,j;
        int m,n;
        for(i=startIndexRow , m=0 ; i<=endIndexRow; i++,m++)
        {
            for(j=startIndexCol, n=0 ; j<=endIndexCol; j++,n++)
            {
                extractedSubArray[m][n] = array[i][j];
            }
        }

        return extractedSubArray;
    }

    //generating all subsets
    public static <T>Set<Set<T>> getAllSubset(Set<T> set) {

        Set<Set<T>> allSubsets = new HashSet<Set<T>>();

        for(int i=1; i<=set.size(); i++)
        {
            allSubsets.addAll(Sets.combinations(set, i));
        }

        return allSubsets;
    }

    //location to gapedorder
    public static List<GapedOrder> locationListToGapedOrderList(List<Location> locationList, Multimap<Restaurant, Order> restaurantOrderMultimap, Multimap<Customer,Order> customerOrderMultimap, HashMap<Order, Double> orderToOrderValueHashMap)
    {
        HashMap<GapedOrder, Integer> gapedOrderIndexOfRestaurantHashMap = new HashMap<GapedOrder, Integer>(); //stores the gaped order with gap=-1, when restaurant is found and Integer value = index of restaurant
        List<GapedOrder> gapedOrderListForLocationList = new ArrayList<GapedOrder>(); //will return this final list
        for(int i=0; i<locationList.size(); i++)
        {
            if(locationList.get(i).getLocationType() == Location.LocationType.RESTAURANT)
            {
                double restaurantInListLatitude = locationList.get(i).getLatitude();
                double restaurantInListLongitude = locationList.get(i).getLongitude();
                int indexOfRestaurant = i;

                //find the restaurant in restaurant-order multimap
                for(Restaurant restaurantInMultimap : restaurantOrderMultimap.keySet())
                {
                    if((restaurantInMultimap.getRestaurantLocation().getLatitude() == restaurantInListLatitude) && restaurantInMultimap.getRestaurantLocation().getLongitude() == restaurantInListLongitude)
                    {
                        List<Order> ordersMappedToRestaurantInList = (List<Order>) restaurantOrderMultimap.get(restaurantInMultimap);

                        for(Order orderPickedFromMultimap: ordersMappedToRestaurantInList )
                        {
                            GapedOrder gapedOrderFromOrderInMultimap = new GapedOrder(orderPickedFromMultimap, -1);
                            gapedOrderIndexOfRestaurantHashMap.put(gapedOrderFromOrderInMultimap, indexOfRestaurant); //put in hashmap
                            gapedOrderListForLocationList.add(gapedOrderFromOrderInMultimap); //put in list of gaped order
                        }
                    }
                }
            }//if for Restaurant found in list of location

            if(locationList.get(i).getLocationType() == Location.LocationType.CUSTOMER)
            {
                double customerInListLatitude = locationList.get(i).getLatitude();
                double customerInListLongitude = locationList.get(i).getLongitude();
                int indexOfCustomer = i;

                //find the customer in customer-order multimap
                for(Customer customerInMultimap : customerOrderMultimap.keySet())
                {
                    if((customerInMultimap.getCustomerLocation().getLatitude() == customerInListLatitude) && (customerInMultimap.getCustomerLocation().getLongitude() == customerInListLongitude))
                    {
                        List<Order> ordersMappedToCustomerInList = (List<Order>) customerOrderMultimap.get(customerInMultimap);
                        //have to find these orders in the hashmap
                        for(Order orderPickedFromMultimap : ordersMappedToCustomerInList) //picking one order from multimap which was mapped to the customer found in the location list

                        {
                            for(GapedOrder gapedOrderFromHashmap : gapedOrderIndexOfRestaurantHashMap.keySet())
                            {
                                if(gapedOrderFromHashmap.getOrder().equals(orderPickedFromMultimap))
                                {
                                    int computedGap = indexOfCustomer - gapedOrderIndexOfRestaurantHashMap.get(gapedOrderFromHashmap) - 1;
                                    gapedOrderFromHashmap.setGap(computedGap); //the gap value is updated in the hashmap

                                    //finding the order in the list of gaped order and updating the gap value
                                    for(GapedOrder gapedOrderInList : gapedOrderListForLocationList)
                                    {
                                        if(gapedOrderInList.equals(gapedOrderFromHashmap))
                                        {
                                            gapedOrderInList.setGap(computedGap);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                //populating orderValue
                for(GapedOrder generatedGapedOrder : gapedOrderListForLocationList)
                {
                    double correctOrderValue = orderToOrderValueHashMap.get(generatedGapedOrder.getOrder());
                    generatedGapedOrder.getOrder().setOrderValue(correctOrderValue);
                }



            }
        }


        return gapedOrderListForLocationList;

    }


    //converting gaped orders to list of locations(paths)
    public static List<Location> gapedOrderListToLocationList(List<GapedOrder> gapedOrders)
    {
        int sumOfGaps=0;
        int maxSizeOfList;
        for(GapedOrder gapedOrder : gapedOrders)
        {
            sumOfGaps += gapedOrder.getGap();
        }
        maxSizeOfList = sumOfGaps + 2*gapedOrders.size();

        Location [] pathFromGapedOrders = new Location[maxSizeOfList];
        int i, startIndex =0;
        HashMap<Location,Integer> alreadyVisitedRestaurant = new HashMap<Location,Integer>();
        HashMap<Location, Integer> alreadyVisitedCustomer = new HashMap<Location, Integer>();
        for(i=0; i<gapedOrders.size(); i++)
        {
            int gap = gapedOrders.get(i).getGap();
            Location orderRestaurantLocation = gapedOrders.get(i).getOrder().getRestaurant().getRestaurantLocation();
            Location orderCustomerLocation = gapedOrders.get(i).getOrder().getCustomer().getCustomerLocation();
            int checkIndex = startIndex + gap +1;



            // restaurant e geche, customer e jayene
            if(  alreadyVisitedRestaurant.containsKey(orderRestaurantLocation) && ! alreadyVisitedCustomer.containsKey(orderCustomerLocation))
            {
                checkIndex = alreadyVisitedRestaurant.get(orderRestaurantLocation) + gap + 1;

                while (!Objects.equals(pathFromGapedOrders[checkIndex], null))
                {
                    checkIndex = checkIndex + 1;
                }

            }
            //restaurant e geche, customer eo geche
            else if(  alreadyVisitedRestaurant.containsKey(orderRestaurantLocation) && alreadyVisitedCustomer.containsKey(orderCustomerLocation)){

                while (!Objects.equals(pathFromGapedOrders[startIndex], null))
                {
                    startIndex = startIndex + 1;
                }

                // as we will be inserting customer at start index and the current gap will not matter.
                checkIndex=startIndex;

                int alreadyPresentIndex = alreadyVisitedCustomer.get(orderCustomerLocation);
                pathFromGapedOrders[alreadyPresentIndex] = null;


            }
            // restaurant e jayene, customer e geche  +++   restaurant eo jayene, customer eo jayene
            else {

                while (!Objects.equals(pathFromGapedOrders[startIndex], null) || !Objects.equals(pathFromGapedOrders[checkIndex], null))
                {
                    startIndex = startIndex + 1;
                    checkIndex = checkIndex + 1;
                }

                if (alreadyVisitedCustomer.containsKey(orderCustomerLocation)) {
                    int alreadyPresentIndex = alreadyVisitedCustomer.get(orderCustomerLocation);
                    pathFromGapedOrders[alreadyPresentIndex] = null;
                }
                alreadyVisitedRestaurant.put(orderRestaurantLocation,startIndex);
                pathFromGapedOrders[startIndex] = orderRestaurantLocation;
            }

            pathFromGapedOrders[checkIndex] = orderCustomerLocation;
            alreadyVisitedCustomer.put(orderCustomerLocation,checkIndex);


            startIndex = nextEmptyLocation(pathFromGapedOrders , startIndex);
            if(startIndex<0)
                break;
        }

        //for removing null entries
        List<Location> pathFromGapedOrdersList = new ArrayList<Location>();
        for(int j =0; j<pathFromGapedOrders.length; j++)
        {
            if(pathFromGapedOrders[j] != null)
            {
                pathFromGapedOrdersList.add(pathFromGapedOrders[j]);
            }
        }

        return pathFromGapedOrdersList;
    }

    public static int nextEmptyLocation(Location[] path, int startIndex)
    {
        int i;
        for(i=startIndex; i<path.length; i++)
        {
            if(path[i] == null)
                return i;
        }
        return -1;
    }


    //for printing the details of gaped order
    public static String formatToPrintGapedOrder(GapedOrder order){
        return "Restaurant-"+order.getOrder().getRestaurant().getRestaurantId()+" Customer-"+order.getOrder().getCustomer().getCustomerId()+" gap="+order.getGap() ;
    }


    //function for dominance test(pareto solution)
    public boolean checkDominance(Path singleParent, Path candidate) throws IOException //returns true if candidate dominates parent
    {
        //flags for comparing parent and candidate
        int orderValue = 0;
        int totalDistance = 0;
        int totalTime = 0;

        boolean strictlyBetter = false;
        boolean noWorse = false;

        //comparing strictly better
        if(candidate.getTotalOrderValue() > singleParent.getTotalOrderValue())
            orderValue = 1; //candidate is strictly better than parent
        if(candidate.getTotalDistance() < singleParent.getTotalDistance())
            totalDistance = 1; //candidate is strictly better than parent
        if(candidate.getTotalTime() < singleParent.getTotalTime())
            totalTime = 1; //candidate is strictly better than parent

        if(orderValue == 1 || totalDistance == 1 || totalTime == 1)
            strictlyBetter = true;


        //comparing no worse
        if(candidate.getTotalOrderValue() >= singleParent.getTotalOrderValue())
            orderValue = 2; //candidate is no worse than parent
        if(candidate.getTotalDistance() <= singleParent.getTotalDistance())
            totalDistance = 2; //candidate is no worse than parent
        if(candidate.getTotalTime() <= singleParent.getTotalTime())
            totalTime = 2; //candidate is no worse than parent

        if(orderValue == 2 && totalDistance == 2 && totalTime == 2)
            noWorse = true;

        //checking dominance
        if(strictlyBetter && noWorse)
            return true; //candidate dominates parent



        return false; //parent dominates candidate
    }

}
