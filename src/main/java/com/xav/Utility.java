package com.xav;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.xav.pojo.*;
import com.xav.receivedData.Order;

import java.util.*;

public class Utility {

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
            /*if(locationList.get(i) == null)
                continue;*/
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

    public static String formatToPrintGapedOrder(GapedOrder order){
        return "Restaurant-"+order.getOrder().getRestaurant().getRestaurantId()+" Customer-"+order.getOrder().getCustomer().getCustomerId()+" gap="+order.getGap() ;
    }

    //function to calculate the total sales amount(order value) for a trip(List<Location>)
    public double getTotalOrderValue(List<Location> tripPathLocationList)
    {

        List<GapedOrder> generatedGapedOrderForTripPathLocationList = new ArrayList<GapedOrder>();


        return 0.0;
    }



    //function to calculate the total distance of the trip(List<Location>)
    public double getTotalDistanceOfTrip(List<Location> tripPath)
    {


        return 0.0;
    }


}
