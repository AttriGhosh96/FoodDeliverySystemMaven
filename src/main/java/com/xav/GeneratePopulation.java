package com.xav;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
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

    private static final double SELECTION_CONSTANT = 0.4;

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
    public Set<List<Location>> createInitialPopulation()
    {

        Set<List<GapedOrder>> gapedOrdersSet = getGapedOrders(orders);

        Set<List<Location>> gapedOrderListToLocationList = new HashSet<>();

        for(List<GapedOrder> singleGapedOrder : gapedOrdersSet)
        {
            List<Location> singleGapedOrderListToLocationList = gapedOrderListToLocationList(singleGapedOrder);
            gapedOrderListToLocationList.add(singleGapedOrderListToLocationList);
        }


        return gapedOrderListToLocationList;
    }

    //


    //for generating gaped orders

    /**
     *
     * @param orders
     * @return set of all combination and permutation of orders-> gapedorders
     */
    public Set<List<GapedOrder>> getGapedOrders(Set<Order> orders)
    {
        //making combinations
        Collection<List<Order>> orderCombinationsNPermutations  = new ArrayList<List<Order>>();

        ImmutableSet<Order> immutableSetOfOrders = ImmutableSet.copyOf(orders);
        for(int combinationSize=1; combinationSize<=immutableSetOfOrders.size(); combinationSize++)
        {
            Set<Set<Order>> combinationsForCombinationSize = Sets.combinations(immutableSetOfOrders, combinationSize);

            for(Set<Order> setOfCombination : combinationsForCombinationSize)
            {
                orderCombinationsNPermutations.addAll(Collections2.permutations(setOfCombination));
            }

        }

        //creating all permutations of the order list received
       // Collection<List<Order>> orderCombinationsNPermutations = Collections2.permutations(orders);



        //for converting all generated permutations of the order list to gaped orders
        Set<List<GapedOrder>> gapedOrdersOfAllCombinationsNPermutations = new HashSet<List<GapedOrder>>();

        for (List<Order> orderPermutation : orderCombinationsNPermutations)
        {
            List<GapedOrder> gapedOrderOnePermutationList = new ArrayList<GapedOrder>();
            for(Order order : orderPermutation) //till with A
            {
                int randomGap = (int) ((2*orderPermutation.size())*Math.random());
                GapedOrder generatedGapedOrder = new GapedOrder(order, randomGap); //gaped order generated for 'order'
                gapedOrderOnePermutationList.add(generatedGapedOrder);

            }
            gapedOrdersOfAllCombinationsNPermutations.add(gapedOrderOnePermutationList);

        }

        return gapedOrdersOfAllCombinationsNPermutations;
    }

    //converting gaped orders to list of locations(paths)
    public List<Location> gapedOrderListToLocationList(List<GapedOrder> gapedOrders)
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

    public int nextEmptyLocation(Location [] path, int startIndex)
    {
        int i;
        for(i=startIndex; i<path.length; i++)
        {
            if(path[i] == null)
                return i;
        }
        return -1;
    }

    //selecting parents for candidate creation
    public List<List<GapedOrder>> selectParents(List<List<GapedOrder>> allParentGapedOrderList)
    {
        //for selecting three parents randomly
        int randomOne = (int) ((allParentGapedOrderList.size()) * Math.random());
        int randomTwo = (int) ((allParentGapedOrderList.size()) * Math.random());
        int randomThree = (int) ((allParentGapedOrderList.size()) * Math.random());

        List<List<GapedOrder>> selectedParents = new ArrayList<List<GapedOrder>>();

        List<GapedOrder> parentOne = new ArrayList<GapedOrder>();
        List<GapedOrder> parentTwo = new ArrayList<GapedOrder>();
        List<GapedOrder> parentThree = new ArrayList<GapedOrder>();

        if((randomOne != randomTwo) && (randomOne != randomThree))
            selectedParents.add(allParentGapedOrderList.get(randomOne));
        else
            selectedParents.add(allParentGapedOrderList.get(randomOne - 1));

        if((randomTwo != randomOne) && (randomTwo != randomThree))
            selectedParents.add(allParentGapedOrderList.get(randomTwo));
        else
            selectedParents.add(allParentGapedOrderList.get(randomTwo - 1));

        if((randomThree != randomOne) && (randomThree != randomTwo))
            selectedParents.add(allParentGapedOrderList.get(randomThree));
        else
            selectedParents.add(allParentGapedOrderList.get(randomThree - 1));

        return selectedParents;
    }



    //function for candidate creation
    public List<Location> candidateCreation(List<List<GapedOrder>> allParentGapedOrderList)
    {

        allParentGapedOrderList.stream().forEach(parent-> {
            System.out.println("-----------------------\n");
            parent.stream().forEach(gapedOrder-> System.out.println(Utility.formatToPrintGapedOrder(gapedOrder)));
        });

        System.out.println("-----------------------\n");

        List<GapedOrder> newCandidateGapedOrder = new ArrayList<GapedOrder>();
        Set<Order> alreadyConsideredOrders = new HashSet<Order>();


        for(int i=0; i<allParentGapedOrderList.size(); i++)
        {
            List<GapedOrder> parent = allParentGapedOrderList.get(i);
            for(GapedOrder gapedOrderOfParent : parent)
            {
                double orderSelectionDeterminant = Math.random();
                if((orderSelectionDeterminant<=SELECTION_CONSTANT) && (! alreadyConsideredOrders.contains(gapedOrderOfParent.getOrder())))
                {
                    System.out.println("Picking -> "+Utility.formatToPrintGapedOrder(gapedOrderOfParent)+" of "+i+"th parent");
                    newCandidateGapedOrder.add(gapedOrderOfParent);
                    alreadyConsideredOrders.add(gapedOrderOfParent.getOrder());
                }
            }

        }


        return gapedOrderListToLocationList(newCandidateGapedOrder);

    }








    //getting the routes for initial population
    private Set<List<Location>> getRandomPathFromOrders(Set<Order> orders)
    {

        return null;
    }






    public static void main(String args[])
    {
        GeneratePopulation obj = new GeneratePopulation();
    }
}
