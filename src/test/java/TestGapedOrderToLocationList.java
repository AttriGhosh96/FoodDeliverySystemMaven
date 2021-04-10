import com.google.common.collect.Multimap;
import com.xav.GeneratePopulation;
import com.xav.POCInitialization;
import com.xav.Utility;
import com.xav.pojo.*;
import com.xav.receivedData.Order;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.xav.Utility.locationListToGapedOrderList;

public class TestGapedOrderToLocationList {
    public static void main(String[] args) {
        Set<Order> orders = new POCInitialization().getOrders();

        int validationCount = 0;
        OrderMaps orderMaps = new OrderMaps();
        orderMaps.updateOrderMaps(orders);

        //Set<List<GapedOrder>> gapedOrdersSet = new POCInitialization().getGapedOrders();
        for (int iterator = 0; iterator<100;iterator++) {


            Set<List<GapedOrder>> gapedOrdersSet = new GeneratePopulation().getGapedOrders(orders);

            List<List<GapedOrder>> gapedOrdersList = gapedOrdersSet.stream().collect(Collectors.toList());

            for (List<GapedOrder> gapedOrders : gapedOrdersList) {



                List<Location> originalPath = new GeneratePopulation().gapedOrderListToLocationList(gapedOrders);


                //checking for creation of multimaps
                OrderMaps objectToCreateMultiMap = new OrderMaps();
                objectToCreateMultiMap.updateOrderMaps(orders);
       /* for (Customer customer: objectToCreateMultiMap.getCustomerToOrderMultimap().keySet()
             ) {
            System.out.println(customer + "-->" + objectToCreateMultiMap.getCustomerToOrderMultimap().get(customer));

        }

        for (Restaurant restaurant: objectToCreateMultiMap.getRestaurantToOrderMultimap().keySet()
             ) {
            System.out.println(restaurant + "-->" + objectToCreateMultiMap.getRestaurantToOrderMultimap().get(restaurant));

        }*/


                //checking the function for list of location to gaped orders
                Multimap<Restaurant, Order> restaurantOrderMultimap = objectToCreateMultiMap.getRestaurantToOrderMultimap();
                Multimap<Customer, Order> customerOrderMultimap = objectToCreateMultiMap.getCustomerToOrderMultimap();
                List<Location> locationListForPath = new GeneratePopulation().gapedOrderListToLocationList(gapedOrders);


                List<GapedOrder> gapedOrderFromLocation = Utility.locationListToGapedOrderList(locationListForPath, restaurantOrderMultimap, customerOrderMultimap);

                List<Location> generatedPath = new GeneratePopulation().gapedOrderListToLocationList(gapedOrderFromLocation);

                boolean equals  = originalPath.equals(generatedPath);
                System.out.println(equals);
                validationCount++;
                if(!equals){

                    System.out.println(gapedOrders);
                    System.out.println();
                    System.out.println("Original");


                    //original
                    originalPath.stream().forEach(location -> {
                        System.out.println(location);
                    });



                    System.out.println("Checking Location List to Gaped Order");
                    System.out.println(gapedOrderFromLocation);



                    System.out.println();
                    System.out.println("Generated");
                    //generated
                    generatedPath.stream().forEach(location -> {
                        System.out.println(location);
                    });

                }

            }
        }
        System.out.println("POC validated -> "+validationCount+" times");
    }

}
