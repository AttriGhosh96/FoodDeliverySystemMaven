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
        Set<List<GapedOrder>> gapedOrdersSet = new GeneratePopulation().getGapedOrders(orders);

        OrderMaps orderMaps = new OrderMaps();
        orderMaps.updateOrderMaps(orders);

        //Set<List<GapedOrder>> gapedOrdersSet = new POCInitialization().getGapedOrders();
        List<List<GapedOrder>> gapedOrdersList = gapedOrdersSet.stream().collect(Collectors.toList());

        List<GapedOrder> gapedOrders = gapedOrdersList.get(0);



        System.out.println(gapedOrders);
        System.out.println();
       /* new GeneratePopulation().gapedOrderListToLocationList(gapedOrders).stream().forEach(location -> {
            System.out.println(location);
        }) ;*/

        System.out.println();

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

        System.out.println();

        //checking the function for list of location to gaped orders
        Multimap<Restaurant, Order> restaurantOrderMultimap= objectToCreateMultiMap.getRestaurantToOrderMultimap();
        Multimap<Customer, Order> customerOrderMultimap = objectToCreateMultiMap.getCustomerToOrderMultimap();
        List<Location> locationListForPath = new GeneratePopulation().gapedOrderListToLocationList(gapedOrders);



        System.out.println("Checking Location List to Gaped Order");
        List<GapedOrder> gapedOrderFromLocation = Utility.locationListToGapedOrderList(locationListForPath, restaurantOrderMultimap, customerOrderMultimap);
        System.out.println(gapedOrderFromLocation);




    }

}
