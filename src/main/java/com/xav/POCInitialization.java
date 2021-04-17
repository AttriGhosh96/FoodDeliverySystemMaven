package com.xav;

import com.google.common.collect.Collections2;
import com.xav.pojo.Customer;
import com.xav.pojo.GapedOrder;
import com.xav.pojo.Location;
import com.xav.pojo.Restaurant;
import com.xav.receivedData.Order;

import java.util.*;

public class POCInitialization {

    //Customer Location
    public List<Location> getCustomerLocation()
    {
        Location customerOne = new Location(Location.LocationType.CUSTOMER, 22.5802, 88.41685);
        Location customerTwo = new Location(Location.LocationType.CUSTOMER, 22.579214, 88.411177);
        Location customerThree = new Location(Location.LocationType.CUSTOMER, 22.584137, 88.428869);
        Location customerFour = new Location(Location.LocationType.CUSTOMER, 22.573755, 88.413849);


        List<Location> customerLocationList = new ArrayList<Location>();
        customerLocationList.add(customerOne);
        customerLocationList.add(customerTwo);
        customerLocationList.add(customerThree);
        customerLocationList.add(customerFour);

        return customerLocationList;
    }

    //Restaurant Location
    public List<Location> getRestaurantLocation()
    {
        Location restaurantOne = new Location(Location.LocationType.RESTAURANT, 22.589764, 88.411746);
        Location restaurantTwo = new Location(Location.LocationType.RESTAURANT, 22.580333, 88.43786);
        Location restaurantThree = new Location(Location.LocationType.RESTAURANT, 22.56898, 88.41151);

        List<Location> restaurantLocationList = new ArrayList<Location>();
        restaurantLocationList.add(restaurantOne);
        restaurantLocationList.add(restaurantTwo);
        restaurantLocationList.add(restaurantThree);

        return restaurantLocationList;
    }

    //Customer Id
    public List<String> getCustomerId()
    {
        String customerIdOne = "C01";
        String customerIdTwo = "C02";
        String customerIdThree = "C03";
        String customerIdFour = "C04";

        List<String> customerIdList = new ArrayList<String>();
        customerIdList.add(customerIdOne);
        customerIdList.add(customerIdTwo);
        customerIdList.add(customerIdThree);
        customerIdList.add(customerIdFour);

        return customerIdList;
    }

    //Restauarant Id
    public List<String> getRestaurantId()
    {
        String restaurantIdOne = "R01";
        String restaurantIdTwo = "R02";
        String restaurantIdThree = "R03";

        List<String> restaurantIdList = new ArrayList<String>();
        restaurantIdList.add(restaurantIdOne);
        restaurantIdList.add(restaurantIdTwo);
        restaurantIdList.add(restaurantIdThree);

        return restaurantIdList;

    }

    //customer
    public List<Customer> getCustomer()
    {
        List<Location> allCustomerLocation = getCustomerLocation();
        List<String> allCustomerId = getCustomerId();

        Customer customerOne = new Customer(allCustomerLocation.get(0), allCustomerId.get(0));
        Customer customerTwo = new Customer(allCustomerLocation.get(1), allCustomerId.get(1));
        Customer customerThree = new Customer(allCustomerLocation.get(2), allCustomerId.get(2));
        Customer customerFour = new Customer(allCustomerLocation.get(3), allCustomerId.get(3));

        List<Customer> allCustomer = new ArrayList<>();
        allCustomer.add(customerOne);
        allCustomer.add(customerTwo);
        allCustomer.add(customerThree);
        allCustomer.add(customerFour);

        return allCustomer;
    }

    //restaurant
    public List<Restaurant> getRestaurant()
    {
        List<Location> allRestaurantLocation = getRestaurantLocation();
        List<String> allRestaurantId = getRestaurantId();

        Restaurant restaurantOne = new Restaurant(allRestaurantLocation.get(0), allRestaurantId.get(0));
        Restaurant restaurantTwo = new Restaurant(allRestaurantLocation.get(1), allRestaurantId.get(1));
        Restaurant restaurantThree = new Restaurant(allRestaurantLocation.get(2), allRestaurantId.get(2));

        List<Restaurant> allRestaurant = new ArrayList<>();
        allRestaurant.add(restaurantOne);
        allRestaurant.add(restaurantTwo);
        allRestaurant.add(restaurantThree);

        return allRestaurant;
    }

    //orderValue
    public List<Double> getOrderValue()
    {
        List<Double> allOrderValues = new ArrayList<Double>();

        allOrderValues.add(530.0);
        allOrderValues.add(635.25);
        allOrderValues.add(270.0);
        allOrderValues.add(442.50);

        return allOrderValues;
    }

    public Set<Order> getOrders()
    {
        List<Customer> allCustomer = getCustomer();
        List<Restaurant> allRestaurant = getRestaurant();

        Order orderOne = new Order(allCustomer.get(0), allRestaurant.get(0), getOrderValue().get(0));
        Order orderTwo = new Order(allCustomer.get(1), allRestaurant.get(1), getOrderValue().get(1));
        Order orderThree = new Order(allCustomer.get(2), allRestaurant.get(0), getOrderValue().get(2));
        Order orderFour = new Order(allCustomer.get(3), allRestaurant.get(2), getOrderValue().get(3));



        Set<Order> orderPlaced = new HashSet<Order>();
        orderPlaced.add(orderOne);
        orderPlaced.add(orderFour);
        orderPlaced.add(orderTwo);
        orderPlaced.add(orderThree);

        return orderPlaced;
    }

    public Set<List<GapedOrder>> getGapedOrders()
    {

        List<Customer> allCustomer = getCustomer();
        List<Restaurant> allRestaurant = getRestaurant();

        Order orderOne = new Order(allCustomer.get(0), allRestaurant.get(0));
        Order orderTwo = new Order(allCustomer.get(1), allRestaurant.get(1));
        Order orderThree = new Order(allCustomer.get(2), allRestaurant.get(0));
        Order orderFour = new Order(allCustomer.get(3), allRestaurant.get(2));

        GapedOrder gapedOrderOne = new GapedOrder(orderOne, 5);
        GapedOrder gapedOrderTwo = new GapedOrder(orderTwo, 0);
        GapedOrder gapedOrderThree = new GapedOrder(orderThree, 5);
        GapedOrder gapedOrderFour = new GapedOrder(orderFour, 1);

        List<GapedOrder> allGapedOrders = new ArrayList<GapedOrder>();
        allGapedOrders.add(gapedOrderOne);
        allGapedOrders.add(gapedOrderFour);
        allGapedOrders.add(gapedOrderTwo);
        allGapedOrders.add(gapedOrderThree);

        Set<List<GapedOrder>> predefinedGapedOrder = new HashSet<>();
        predefinedGapedOrder.add(allGapedOrders);

        return predefinedGapedOrder;
    }
}
