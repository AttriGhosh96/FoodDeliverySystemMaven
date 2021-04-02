import com.google.common.collect.Lists;
import com.xav.GeneratePopulation;
import com.xav.POCInitialization;
import com.xav.pojo.GapedOrder;
import com.xav.pojo.Location;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestGapedOrderToLocationList {
    public static void main(String[] args) {
        Set<List<GapedOrder>> gapedOrdersSet = new GeneratePopulation().getGapedOrders(new POCInitialization().getOrders());
        List<List<GapedOrder>> gapedOrdersList = gapedOrdersSet.stream().collect(Collectors.toList());

        List<GapedOrder> gapedOrders = gapedOrdersList.get(0);



        System.out.println(gapedOrders);
        System.out.println();
        new GeneratePopulation().gapedOrderToPath(gapedOrders).stream().forEach(location -> {
            System.out.println(location);
        }) ;

    }

}
