package com.xav.pojo;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.xav.receivedData.Order;

import java.util.HashMap;
import java.util.Set;

public class OrderMaps {


    private Set<Order> orders;

    private Multimap<Customer,Order> customerToOrderMultimap;
    private Multimap<Restaurant,Order> restaurantToOrderMultimap;
    private HashMap<Order, Double> orderToOrderValueHashMap;

    public HashMap<Order, Double> getOrderToOrderValueHashMap() {
        return orderToOrderValueHashMap;
    }

    public void setOrderToOrderValueHashMap(HashMap<Order, Double> orderToOrderValueHashMap) {
        this.orderToOrderValueHashMap = orderToOrderValueHashMap;
    }

    public void updateOrderMaps(Set<Order> receivedOrders){
        this.orders = receivedOrders;
        // customer multimap creation
        customerToOrderMultimap = ArrayListMultimap.create();
        for(Order order : receivedOrders)
        {
            customerToOrderMultimap.put(order.getCustomer(), order);
        }

        //restaurant multimap creation
        restaurantToOrderMultimap = ArrayListMultimap.create();
        for(Order order : receivedOrders)
        {
            restaurantToOrderMultimap.put(order.getRestaurant(), order);
        }

        //hashmap orderToOrder
        for(Order order : receivedOrders)
        {
            orderToOrderValueHashMap.put(order, order.getOrderValue());

        }

    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Multimap<Customer, Order> getCustomerToOrderMultimap() {
        return customerToOrderMultimap;
    }

    public void setCustomerToOrderMultimap(Multimap<Customer, Order> customerToOrderMultimap) {
        this.customerToOrderMultimap = customerToOrderMultimap;
    }

    public Multimap<Restaurant, Order> getRestaurantToOrderMultimap() {
        return restaurantToOrderMultimap;
    }

    public void setRestaurantToOrderMultimap(Multimap<Restaurant, Order> restaurantToOrderMultimap) {
        this.restaurantToOrderMultimap = restaurantToOrderMultimap;
    }
}
