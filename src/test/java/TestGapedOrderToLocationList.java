import com.google.common.collect.Lists;
import com.xav.GeneratePopulation;
import com.xav.POCInitialization;
import com.xav.pojo.GapedOrder;
import com.xav.pojo.Location;
import com.xav.pojo.OrderMaps;
import com.xav.receivedData.Order;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        new GeneratePopulation().gapedOrderToPath(gapedOrders).stream().forEach(location -> {
            System.out.println(location);
        }) ;

    }

}
