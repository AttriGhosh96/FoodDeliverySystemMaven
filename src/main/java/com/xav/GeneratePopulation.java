package com.xav;
import com.google.common.collect.Collections2;
import com.xav.mapQuest.routeMatrix.RouteMatrix;
import com.xav.mapQuest.routeMatrix.RouteMatrixInterface;
import com.xav.pojo.Customer;
import com.xav.pojo.GapedOrder;
import com.xav.pojo.Location;
import com.xav.pojo.Restaurant;
import com.xav.receivedData.Order;

import java.util.*;
import java.util.stream.Collectors;

public class GeneratePopulation {

    private Set<Order> orders;
    private double [][] customerCustomerDistanceMatrix;
    private double [][] customerRestaurantDistanceMatrix;
    private double [][] restaurantRestaurantDistanceMatrix;
    private RouteMatrixInterface routeMatrix;

    public GeneratePopulation(Set<Order> orders, double[][] customerCustomerDistanceMatrix, double[][] customerRestaurantDistanceMatrix, double[][] restaurantRestaurantDistanceMatrix) {
        //todo add routematrix
        this.orders = orders;

        this.customerCustomerDistanceMatrix = customerCustomerDistanceMatrix;
        this.customerRestaurantDistanceMatrix = customerRestaurantDistanceMatrix;
        this.restaurantRestaurantDistanceMatrix = restaurantRestaurantDistanceMatrix;
    }

    public GeneratePopulation() {
        initialisation();
    }

    public void initialisation() {
        POCInitialization pocObj = new POCInitialization();
        orders = pocObj.getOrders();



        //customerCustomerDistanceMatrix = new double[noOfUniqueCustomers][noOfUniqueCustomers];
        // when API will be called it will be called with a list of locations

        //customerCustomerDistanceMatrix =new double [][] {{0, 1.115, 1.255, 1.755},
//                                                        {0.859, 0, 1.622, 1.001},
//                                                        {1.519, 1.903, 0, 2.395},
//                                                        {1.169, 0.727, 2.21, 0}};
//
//        //restaurantRestaurantDistanceMatrix = new double[][]{{0, 2.874, 3.212},
//                                                            {2.809, 0, 3.543},
//                                                            {2.149, 2.694, 0}};

        List<Location> customerThenRestaurants =  new ArrayList<>();
        routeMatrix = new RouteMatrix();

        Set<Customer> allUniqueCustomer = getUniqueCustomers();
        Set<Restaurant> allUniqueRestaurant = getUniqueRestaurants();

        //matrix
        int noOfUniqueCustomers = allUniqueCustomer.size();
        int noOfUniqueRestaurants = allUniqueRestaurant.size();

        customerThenRestaurants.addAll(allUniqueCustomer.stream().map(customer->customer.getCustomerLocation()).collect(Collectors.toList()));
        customerThenRestaurants.addAll(allUniqueRestaurant.stream().map(restaurant -> restaurant.getRestaurantLocation()).collect(Collectors.toList()));

        double [][] allToAll = routeMatrix.getAllToAll(customerThenRestaurants) ;

        customerCustomerDistanceMatrix = Utility.extractSubArray(allToAll, 0, noOfUniqueCustomers-1, 0, noOfUniqueCustomers-1);
        restaurantRestaurantDistanceMatrix = Utility.extractSubArray(allToAll, noOfUniqueCustomers, (noOfUniqueCustomers + noOfUniqueRestaurants)-1, noOfUniqueCustomers, (noOfUniqueCustomers + noOfUniqueRestaurants)-1 );
        customerRestaurantDistanceMatrix = Utility.extractSubArray(allToAll, 0, noOfUniqueCustomers-1, noOfUniqueCustomers, (noOfUniqueCustomers + noOfUniqueRestaurants)-1);

        System.out.println("Customer-Customer");
        display(customerCustomerDistanceMatrix);
        System.out.println("Restaurant-Restaurant");
        display(restaurantRestaurantDistanceMatrix);
        System.out.println("Customer-Restaurant");
        display(customerRestaurantDistanceMatrix);


    }




    public Set<Customer> getUniqueCustomers()
    {
        Set<Customer> setOfCustomer = new HashSet<Customer>();

        for( Order order : orders)
        {
            setOfCustomer.add(order.getCustomer());
        }
        return setOfCustomer;
    }

    public Set<Restaurant> getUniqueRestaurants()
    {
        Set<Restaurant> setOfRestaurant = new HashSet<Restaurant>();

        for( Order order : orders)
        {
            setOfRestaurant.add(order.getRestaurant());
        }
        return setOfRestaurant;
    }

    public void display(double [][] array)
    {
        int i,j;
        for(i=0; i<array.length; i++)
        {
            for(j=0; j<array[i].length; j++)
            {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    //creation of initial population
    public List<List<Location>> createInitialPopulation()
    {

        Set<Set<Order>> allSubsetOrder = new HashSet<Set<Order>>();
        allSubsetOrder = Utility.getAllSubset(orders); // till with A
        for(Set<Order> oneSubsetOrder : allSubsetOrder)
        {
            getGapedOrders(oneSubsetOrder);
        }


        return null;
    }


    //for generating gaped orders
    public Set<List<GapedOrder>> getGapedOrders(Set<Order> orders)
    {
        Collection<List<Order>> orderPermutations = Collections2.permutations(orders);

        Set<List<GapedOrder>> gapedorders = new HashSet<List<GapedOrder>>();

        for (List<Order> orderPermutation : orderPermutations)
        {
            for(Order order : orderPermutation) //till with A
            {
                int randomGap = (int) ((2*orderPermutation.size())*Math.random());

            }

        }


        return null;
    }




    //getting the routes for initial population
    private Set<List<Location>> getRandomPathFromOrders(Set<Order> orders)
    {

        return null;
    }

    //permutation function
    public List<List<Order>> getPermutationOfSubList(List<Order> subListOfOriginalOrderList)
    {
        List<List<Order>> toBeReturnedListOfPermutation = new ArrayList<>();
        Order firstOrder = subListOfOriginalOrderList.remove(0);
        List<List<Order>> nextPermutation = getPermutationOfSubList(subListOfOriginalOrderList);
        for(List<Order> onePermutation : nextPermutation)
        {
            for(int i=0; i<onePermutation.size(); i++)
            {
                List<Order> temp = new ArrayList<>(onePermutation);
                temp.add(i, firstOrder);
                toBeReturnedListOfPermutation.add(temp);
            }
        }
        return toBeReturnedListOfPermutation;
    }

    //function to check relative ordering of customer and restaurant
    public boolean checkRelativeOrderingCR(List<Order> orderList)
    {
        return true;
    }


    public static void main(String args[])
    {
        GeneratePopulation obj = new GeneratePopulation();
    }
}
