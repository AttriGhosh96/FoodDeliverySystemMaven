package com.xav;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.xav.mapQuest.routeMatrix.RouteMatrix;
import com.xav.mapQuest.routeMatrix.RouteMatrixInterface;
import com.xav.pojo.*;
import com.xav.receivedData.Order;
import com.xav.web.TestCaseOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GeneratePopulation {

    private static final double SELECTION_CONSTANT = 0.40;

    private static GeneratePopulation generatePopulationObject;

    static {
        try {
            generatePopulationObject = new GeneratePopulation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GeneratePopulation  getGeneratePopulationInstance(){
        return  generatePopulationObject;
    }

    private Set<Order> orders;
    private double [][] allToAllDistanceMatrix;
    private double [][] allToAllTimeMatrix;

    public double[][] getAllToAllDistanceMatrix() {
        return allToAllDistanceMatrix;
    }

    public double[][] getAllToAllTimeMatrix() {
        return allToAllTimeMatrix;
    }

    public Map<Location, Integer> getLocationIndexMap() {
        return locationIndexMap;
    }

    private Map<Location, Integer> locationIndexMap;
    private double [][] customerCustomerDistanceMatrix;
    private double [][] customerRestaurantDistanceMatrix;
    private double [][] restaurantRestaurantDistanceMatrix;
    private RouteMatrixInterface routeMatrix;

//    public GeneratePopulation(Set<Order> orders, double[][] customerCustomerDistanceMatrix, double[][] customerRestaurantDistanceMatrix, double[][] restaurantRestaurantDistanceMatrix) {
//        //todo add routematrix
//        this.orders = orders;
//
//        this.customerCustomerDistanceMatrix = customerCustomerDistanceMatrix;
//        this.customerRestaurantDistanceMatrix = customerRestaurantDistanceMatrix;
//        this.restaurantRestaurantDistanceMatrix = restaurantRestaurantDistanceMatrix;
//    }

    private GeneratePopulation() throws IOException {
        initialisation();
    }





    public void initialisation() throws IOException {
//        POCInitialization pocObj = new POCInitialization();
//        orders = pocObj.getOrders();
        TestCaseOne obj = new TestCaseOne();
        orders = obj.getOrders();




        List<Location> customerThenRestaurants =  new ArrayList<>();
        routeMatrix = new RouteMatrix();

        Set<Customer> allUniqueCustomer = getUniqueCustomers();
        Set<Restaurant> allUniqueRestaurant = getUniqueRestaurants();

        List<Customer> allUniqueCustomerList = allUniqueCustomer.stream().collect(Collectors.toList());
        List<Restaurant> allUniqueRestaurantList = allUniqueRestaurant.stream().collect(Collectors.toList());

        Map<Location , Integer> locationIndexMap = new HashMap<Location , Integer>();

        int index = 0;
        for(Customer customer:allUniqueCustomerList){
            locationIndexMap.put(customer.getCustomerLocation() , index);
            customerThenRestaurants.add(index , customer.getCustomerLocation());
            index++;
        }

        for(Restaurant restaurant: allUniqueRestaurantList){
            locationIndexMap.put(restaurant.getRestaurantLocation(),index);
            customerThenRestaurants.add(index , restaurant.getRestaurantLocation());
            index++;
        }



        //matrix
        int noOfUniqueCustomers = allUniqueCustomer.size();
        int noOfUniqueRestaurants = allUniqueRestaurant.size();

        //customerThenRestaurants.addAll(allUniqueCustomer.stream().map(customer->customer.getCustomerLocation()).collect(Collectors.toList()));
        //customerThenRestaurants.addAll(allUniqueRestaurant.stream().map(restaurant -> restaurant.getRestaurantLocation()).collect(Collectors.toList()));

        double [][] allToAllDistance = routeMatrix.getAllToAllDistanceMatrix(customerThenRestaurants);
        double [][] allToAllTime = routeMatrix.getAllToAllTimeMatrix(customerThenRestaurants);
        this.allToAllDistanceMatrix = allToAllDistance;
        this.allToAllTimeMatrix = allToAllTime;
        this.locationIndexMap = locationIndexMap;

        System.out.println(customerThenRestaurants);
        System.out.println(locationIndexMap);

//        //printing allToALLTimeMatrix
//        for(int i=0; i<allToAllTimeMatrix.length; i++)
//        {
//            for(int j=0; j<allToAllTimeMatrix[i].length; j++) {
//                System.out.print(allToAllTimeMatrix[i][j] + " ");
//
//            }
//            System.out.println();
//        }

        customerCustomerDistanceMatrix = Utility.extractSubArray(allToAllDistance, 0, noOfUniqueCustomers-1, 0, noOfUniqueCustomers-1);
        restaurantRestaurantDistanceMatrix = Utility.extractSubArray(allToAllDistance, noOfUniqueCustomers, (noOfUniqueCustomers + noOfUniqueRestaurants)-1, noOfUniqueCustomers, (noOfUniqueCustomers + noOfUniqueRestaurants)-1 );
        customerRestaurantDistanceMatrix = Utility.extractSubArray(allToAllDistance, 0, noOfUniqueCustomers-1, noOfUniqueCustomers, (noOfUniqueCustomers + noOfUniqueRestaurants)-1);



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


    //creation of initial population
    public Set<Path> createInitialPopulation()
    {

        Set<List<GapedOrder>> gapedOrdersSet = getGapedOrders(orders);


        Set<Path> initialPopulationPaths = new HashSet<Path>();

        for(List<GapedOrder> singleGapedOrder : gapedOrdersSet)
        {
            List<Location> singleGapedOrderListToLocationList = Utility.gapedOrderListToLocationList(singleGapedOrder);
            initialPopulationPaths.add(new Path(singleGapedOrder));
        }


        return initialPopulationPaths;
    }


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


        //for converting all generated permutations of the order list to gaped orders
        Set<List<GapedOrder>> gapedOrdersOfAllCombinationsNPermutations = new HashSet<List<GapedOrder>>();
        //make subset of orderCombinationsNPermutation



        int count = 0;
        int size = orderCombinationsNPermutations.size();
        for (List<Order> orderPermutation : orderCombinationsNPermutations)
        {
            System.out.println(count+"/"+size);
            count++;

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

//    //converting gaped orders to list of locations(paths)
//    public List<Location> gapedOrderListToLocationList(List<GapedOrder> gapedOrders)
//    {
//        int sumOfGaps=0;
//        int maxSizeOfList;
//        for(GapedOrder gapedOrder : gapedOrders)
//        {
//            sumOfGaps += gapedOrder.getGap();
//        }
//        maxSizeOfList = sumOfGaps + 2*gapedOrders.size();
//
//        Location [] pathFromGapedOrders = new Location[maxSizeOfList];
//        int i, startIndex =0;
//        HashMap<Location,Integer> alreadyVisitedRestaurant = new HashMap<Location,Integer>();
//        HashMap<Location, Integer> alreadyVisitedCustomer = new HashMap<Location, Integer>();
//        for(i=0; i<gapedOrders.size(); i++)
//        {
//            int gap = gapedOrders.get(i).getGap();
//            Location orderRestaurantLocation = gapedOrders.get(i).getOrder().getRestaurant().getRestaurantLocation();
//            Location orderCustomerLocation = gapedOrders.get(i).getOrder().getCustomer().getCustomerLocation();
//            int checkIndex = startIndex + gap +1;
//
//
//
//            // restaurant e geche, customer e jayene
//            if(  alreadyVisitedRestaurant.containsKey(orderRestaurantLocation) && ! alreadyVisitedCustomer.containsKey(orderCustomerLocation))
//            {
//                checkIndex = alreadyVisitedRestaurant.get(orderRestaurantLocation) + gap + 1;
//
//                while (!Objects.equals(pathFromGapedOrders[checkIndex], null))
//                {
//                    checkIndex = checkIndex + 1;
//                }
//
//            }
//            //restaurant e geche, customer eo geche
//            else if(  alreadyVisitedRestaurant.containsKey(orderRestaurantLocation) && alreadyVisitedCustomer.containsKey(orderCustomerLocation)){
//
//                while (!Objects.equals(pathFromGapedOrders[startIndex], null))
//                {
//                    startIndex = startIndex + 1;
//                }
//
//                // as we will be inserting customer at start index and the current gap will not matter.
//                checkIndex=startIndex;
//
//                int alreadyPresentIndex = alreadyVisitedCustomer.get(orderCustomerLocation);
//                pathFromGapedOrders[alreadyPresentIndex] = null;
//
//
//            }
//            // restaurant e jayene, customer e geche  +++   restaurant eo jayene, customer eo jayene
//            else {
//
//                while (!Objects.equals(pathFromGapedOrders[startIndex], null) || !Objects.equals(pathFromGapedOrders[checkIndex], null))
//                {
//                    startIndex = startIndex + 1;
//                    checkIndex = checkIndex + 1;
//                }
//
//                if (alreadyVisitedCustomer.containsKey(orderCustomerLocation)) {
//                    int alreadyPresentIndex = alreadyVisitedCustomer.get(orderCustomerLocation);
//                    pathFromGapedOrders[alreadyPresentIndex] = null;
//                }
//                alreadyVisitedRestaurant.put(orderRestaurantLocation,startIndex);
//                pathFromGapedOrders[startIndex] = orderRestaurantLocation;
//            }
//
//            pathFromGapedOrders[checkIndex] = orderCustomerLocation;
//            alreadyVisitedCustomer.put(orderCustomerLocation,checkIndex);
//
//
//            startIndex = nextEmptyLocation(pathFromGapedOrders , startIndex);
//            if(startIndex<0)
//                break;
//        }
//
//        //for removing null entries
//        List<Location> pathFromGapedOrdersList = new ArrayList<Location>();
//        for(int j =0; j<pathFromGapedOrders.length; j++)
//        {
//            if(pathFromGapedOrders[j] != null)
//            {
//                pathFromGapedOrdersList.add(pathFromGapedOrders[j]);
//            }
//        }
//
//        return pathFromGapedOrdersList;
//    }

//    public int nextEmptyLocation(Location [] path, int startIndex)
//    {
//        int i;
//        for(i=startIndex; i<path.length; i++)
//        {
//            if(path[i] == null)
//                return i;
//        }
//        return -1;
//    }

    //selecting parents for candidate creation
    public List<Path> selectParents(List<Path> allParentPathList)
    {

        int selectionCount = 3;
        Set<Path> selectedParents = new HashSet<Path>();

        Random random = new Random();

        while(selectedParents.size()<selectionCount){
            selectedParents.add(allParentPathList.get(random.nextInt(allParentPathList.size())));
        }

        return selectedParents.stream().collect(Collectors.toList());
    }


    //function for candidate creation
    public Path createCandidate(List<Path> selectedParentPathList)
    {

//        allParentGapedOrderList.stream().forEach(parent-> {
//            System.out.println("-----------------------\n");
//            parent.stream().forEach(gapedOrder-> System.out.println(Utility.formatToPrintGapedOrder(gapedOrder)));
//        });
//
//        System.out.println("-----------------------\n");

        List<List<GapedOrder>> allParentGapedOrderList = selectedParentPathList.stream().map(path -> path.getPathGapedOrders()).collect(Collectors.toList());

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
//                    System.out.println("Picking -> "+Utility.formatToPrintGapedOrder(gapedOrderOfParent)+" of "+i+"th parent");
                    newCandidateGapedOrder.add(gapedOrderOfParent);
                    alreadyConsideredOrders.add(gapedOrderOfParent.getOrder());
                }
            }

        }
        if(newCandidateGapedOrder.size()!=0)
        return  new Path(newCandidateGapedOrder);


        return null;

    }



    //getting the routes for initial population
    private Set<List<Location>> getRandomPathFromOrders(Set<Order> orders)
    {

        return null;
    }






}
