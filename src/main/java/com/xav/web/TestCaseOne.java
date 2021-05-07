package com.xav.web;

import com.xav.pojo.Customer;
import com.xav.pojo.Location;
import com.xav.pojo.Restaurant;
import com.xav.receivedData.Order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestCaseOne {

    //Customer Location
    public List<Location> getCustomerLocation()
    {
        Location customerOne = new Location(Location.LocationType.CUSTOMER, 22.577518, 88.405890);
        Location customerTwo = new Location(Location.LocationType.CUSTOMER, 22.576231, 88.412638);
        Location customerThree = new Location(Location.LocationType.CUSTOMER, 22.573192, 88.410280);
        Location customerFour = new Location(Location.LocationType.CUSTOMER, 22.592100, 88.404658);
        Location customerFive = new Location(Location.LocationType.CUSTOMER, 22.590362, 88.431024);
        Location customerSix = new Location(Location.LocationType.CUSTOMER, 22.600800, 88.415727);
        Location customerSeven = new Location(Location.LocationType.CUSTOMER, 22.597907, 88.413761);
        Location customerEight = new Location(Location.LocationType.CUSTOMER, 22.596715, 88.410593);
        Location customerNine = new Location(Location.LocationType.CUSTOMER, 22.591943, 88.423199);
        Location customerTen = new Location(Location.LocationType.CUSTOMER, 22.594115, 88.430548);


        List<Location> customerLocationList = new ArrayList<Location>();
        customerLocationList.add(customerOne);
        customerLocationList.add(customerTwo);
        customerLocationList.add(customerThree);
        customerLocationList.add(customerFour);
        customerLocationList.add(customerFive);
        customerLocationList.add(customerSix);
        customerLocationList.add(customerSeven);
        customerLocationList.add(customerEight);
        customerLocationList.add(customerNine);
        customerLocationList.add(customerTen);

        return customerLocationList;
    }

    //Restaurant Location
    public List<Location> getRestaurantLocation()
    {
        Location restaurantOne = new Location(Location.LocationType.RESTAURANT, 22.571165240022996, 88.40510026271018);
        Location restaurantTwo = new Location(Location.LocationType.RESTAURANT, 22.58738442174909, 88.40800684548245);
        Location restaurantThree = new Location(Location.LocationType.RESTAURANT, 22.580798567619606, 88.43801613337213);
        Location restaurantFour = new Location(Location.LocationType.RESTAURANT, 22.59537598390024, 88.41950855423269);
        Location restaurantFive = new Location(Location.LocationType.RESTAURANT, 22.572473752663957, 88.43377077233932);
        Location restaurantSix = new Location(Location.LocationType.RESTAURANT, 22.576986713074163, 88.42804583989802);

        List<Location> restaurantLocationList = new ArrayList<Location>();
        restaurantLocationList.add(restaurantOne);
        restaurantLocationList.add(restaurantTwo);
        restaurantLocationList.add(restaurantThree);
        restaurantLocationList.add(restaurantFour);
        restaurantLocationList.add(restaurantFive);
        restaurantLocationList.add(restaurantSix);

        return restaurantLocationList;
    }

    //Customer Id
    public List<String> getCustomerId()
    {
        String customerIdOne = "C05";
        String customerIdTwo = "C06";
        String customerIdThree = "C07";
        String customerIdFour = "C08";
        String customerIdFive = "C09";
        String customerIdSix = "C10";
        String customerIdSeven = "C11";
        String customerIdEight = "C12";
        String customerIdNine = "C13";
        String customerIdTen = "C14";

        List<String> customerIdList = new ArrayList<String>();
        customerIdList.add(customerIdOne);
        customerIdList.add(customerIdTwo);
        customerIdList.add(customerIdThree);
        customerIdList.add(customerIdFour);
        customerIdList.add(customerIdFive);
        customerIdList.add(customerIdSix);
        customerIdList.add(customerIdSeven);
        customerIdList.add(customerIdEight);
        customerIdList.add(customerIdNine);
        customerIdList.add(customerIdTen);

        return customerIdList;
    }

    //Restauarant Id
    public List<String> getRestaurantId()
    {
        String restaurantIdOne = "R04";
        String restaurantIdTwo = "R05";
        String restaurantIdThree = "R06";
        String restaurantIdFour = "R07";
        String restaurantIdFive = "R08";
        String restaurantIdSix = "R09";

        List<String> restaurantIdList = new ArrayList<String>();
        restaurantIdList.add(restaurantIdOne);
        restaurantIdList.add(restaurantIdTwo);
        restaurantIdList.add(restaurantIdThree);
        restaurantIdList.add(restaurantIdFour);
        restaurantIdList.add(restaurantIdFive);
        restaurantIdList.add(restaurantIdSix);

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
        Customer customerFive = new Customer(allCustomerLocation.get(4), allCustomerId.get(4));
        Customer customerSix = new Customer(allCustomerLocation.get(5), allCustomerId.get(5));
        Customer customerSeven = new Customer(allCustomerLocation.get(6), allCustomerId.get(6));
        Customer customerEight = new Customer(allCustomerLocation.get(7), allCustomerId.get(7));
        Customer customerNine = new Customer(allCustomerLocation.get(8), allCustomerId.get(8));
        Customer customerTen = new Customer(allCustomerLocation.get(9), allCustomerId.get(9));

        List<Customer> allCustomer = new ArrayList<>();
        allCustomer.add(customerOne);
        allCustomer.add(customerTwo);
        allCustomer.add(customerThree);
        allCustomer.add(customerFour);
        allCustomer.add(customerFive);
        allCustomer.add(customerSix);
        allCustomer.add(customerSeven);
        allCustomer.add(customerEight);
        allCustomer.add(customerNine);
        allCustomer.add(customerTen);

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
        Restaurant restaurantFour = new Restaurant(allRestaurantLocation.get(3), allRestaurantId.get(3));
        Restaurant restaurantFive = new Restaurant(allRestaurantLocation.get(4), allRestaurantId.get(4));
        Restaurant restaurantSix = new Restaurant(allRestaurantLocation.get(5), allRestaurantId.get(5));

        List<Restaurant> allRestaurant = new ArrayList<>();
        allRestaurant.add(restaurantOne);
        allRestaurant.add(restaurantTwo);
        allRestaurant.add(restaurantThree);
        allRestaurant.add(restaurantFour);
        allRestaurant.add(restaurantFive);
        allRestaurant.add(restaurantSix);

        return allRestaurant;
    }

    //orderValue
    public List<Double> getOrderValue()
    {
        List<Double> allOrderValues = new ArrayList<Double>();

        allOrderValues.add(535.50);
        allOrderValues.add(234.0);
        allOrderValues.add(432.0);
        allOrderValues.add(192.0);
        allOrderValues.add(349.0);
        allOrderValues.add(551.0);
        allOrderValues.add(611.10);
        allOrderValues.add(440.90);
        allOrderValues.add(410.0);
        allOrderValues.add(610.0);

        return allOrderValues;
    }

    //all orders
    public Set<Order> getOrders()
    {
        List<Customer> allCustomer = getCustomer();
        List<Restaurant> allRestaurant = getRestaurant();
        List<Double> allOrderValues = getOrderValue();

        Order orderOne = new Order(allCustomer.get(0), allRestaurant.get(0), allOrderValues.get(0));
        Order orderTwo = new Order(allCustomer.get(1), allRestaurant.get(2), allOrderValues.get(1));
        Order orderThree = new Order(allCustomer.get(2), allRestaurant.get(1), allOrderValues.get(2));
        Order orderFour = new Order(allCustomer.get(3), allRestaurant.get(4), allOrderValues.get(3));
        Order orderFive = new Order(allCustomer.get(4), allRestaurant.get(0), allOrderValues.get(4));
        Order orderSix = new Order(allCustomer.get(5), allRestaurant.get(5), allOrderValues.get(5));
        Order orderSeven = new Order(allCustomer.get(6), allRestaurant.get(2), allOrderValues.get(6));
        Order orderEight = new Order(allCustomer.get(7), allRestaurant.get(3), allOrderValues.get(7));
        Order orderNine = new Order(allCustomer.get(8), allRestaurant.get(5), allOrderValues.get(8));
        Order orderTen = new Order(allCustomer.get(9), allRestaurant.get(1), allOrderValues.get(9));



        Set<Order> orderPlaced = new HashSet<Order>();
        orderPlaced.add(orderOne);
        orderPlaced.add(orderTwo);
        orderPlaced.add(orderThree);
        orderPlaced.add(orderFour);
        orderPlaced.add(orderFive);
        orderPlaced.add(orderSix);
        orderPlaced.add(orderSeven);
        orderPlaced.add(orderEight);
        orderPlaced.add(orderNine);
        orderPlaced.add(orderTen);

        return orderPlaced;
    }

}
