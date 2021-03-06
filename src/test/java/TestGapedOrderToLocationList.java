import com.google.common.collect.Multimap;
import com.xav.GeneratePopulation;
import com.xav.POCInitialization;
import com.xav.Utility;
import com.xav.pojo.*;
import com.xav.receivedData.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.xav.Utility.locationListToGapedOrderList;

public class TestGapedOrderToLocationList {
    public static void main(String[] args) {
        Set<Order> orders = new POCInitialization().getOrders();

        int validationCount = 0;

       for (int iterator = 0; iterator<1; iterator++) {


            Set<List<GapedOrder>> gapedOrdersSet = GeneratePopulation.getGeneratePopulationInstance().getGapedOrders(orders);


            List<List<GapedOrder>> gapedOrdersList = gapedOrdersSet.stream().collect(Collectors.toList());

            //checking candidate creation
//           List<List<GapedOrder>> selectedParents = new GeneratePopulation().selectParents(gapedOrdersList);
//           List<Location> newCandidate = new GeneratePopulation().candidateCreation(selectedParents);
//           System.out.println("New Candidate: ");
//           newCandidate.stream().forEach(candidateLocation ->{
//               System.out.println(candidateLocation);
//           });



            for (List<GapedOrder> gapedOrders : gapedOrdersList) {

                OrderMaps orderMaps = new OrderMaps();
                orderMaps.updateOrderMaps(gapedOrders.stream().map(gapedOrder1->gapedOrder1.getOrder()).collect(Collectors.toSet()));


                List<Location> originalPath = new Utility().gapedOrderListToLocationList(gapedOrders);


                //checking the function for list of location to gaped orders
               Multimap<Restaurant, Order> restaurantOrderMultimap = orderMaps.getRestaurantToOrderMultimap();
               Multimap<Customer, Order> customerOrderMultimap = orderMaps.getCustomerToOrderMultimap();
               HashMap<Order, Double> orderToOrderValueHashMap = orderMaps.getOrderToOrderValueHashMap();




                List<GapedOrder> gapedOrderFromLocation = Utility.locationListToGapedOrderList(originalPath, restaurantOrderMultimap, customerOrderMultimap, orderToOrderValueHashMap);


                List<Location> generatedPath = new Utility().gapedOrderListToLocationList(gapedOrderFromLocation);

//                double totalOrderValue = Utility.getTotalOrderValue(generatedPath, restaurantOrderMultimap, customerOrderMultimap, orderToOrderValueHashMap);
//                System.out.println(totalOrderValue);


                boolean equals  = originalPath.equals(generatedPath);
//                System.out.println(equals);
                validationCount++;
//                if(!equals){
//
//                    System.out.println(gapedOrders);
//                    System.out.println();
//                    System.out.println("Original");
//
//
//                    //original
//                    originalPath.stream().forEach(location -> {
//                        System.out.println(location);
//                    });
//
//
//
//                    System.out.println("Checking Location List to Gaped Order");
//                    System.out.println(gapedOrderFromLocation);
//
//
//
//                    System.out.println();
//                    System.out.println("Generated");
//                    //generated
//                    generatedPath.stream().forEach(location -> {
//                        System.out.println(location);
//                    });
//
//                }

            }
        }
 //       System.out.println("POC validated -> "+validationCount+" times");


    }

}
