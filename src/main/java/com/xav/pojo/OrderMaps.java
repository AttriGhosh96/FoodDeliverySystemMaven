package com.xav.pojo;

import com.google.common.collect.Multimap;
import com.xav.receivedData.Order;

import java.util.Set;

public class OrderMaps {


    private Set<Order> orders;

    private Multimap<Customer,Order> customerToOrderMultimap;
    private Multimap<Restaurant,Order> restaurantToOrderMultimap;

    public void updateOrderMaps( Set<Order> receivedOrders){
        this.orders = receivedOrders;
        //multimap creation

    }





}
