package com.xav.receivedData;

import com.xav.pojo.Customer;
import com.xav.pojo.Restaurant;

public class Order {
   public Order(Customer customer, Restaurant restaurant, double orderValue) {
      this.customer = customer;
      this.restaurant = restaurant;
      this.orderValue = orderValue;
   }

   private Customer customer;
   private Restaurant restaurant;
   private double orderValue;

   public double getOrderValue() {
      return orderValue;
   }

   public void setOrderValue(double orderValue) {
      this.orderValue = orderValue;
   }

   public Customer getCustomer() {
      return customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }

   public Restaurant getRestaurant() {
      return restaurant;
   }

   public void setRestaurant(Restaurant restaurant) {
      this.restaurant = restaurant;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Order)) return false;

      Order order = (Order) o;

      if (!getCustomer().equals(order.getCustomer())) return false;
      return getRestaurant().equals(order.getRestaurant());
   }

   @Override
   public int hashCode() {
      int result = getCustomer().hashCode();
      result = 31 * result + getRestaurant().hashCode();
      return result;
   }

   public Order(Customer customer, Restaurant restaurant) {
      this.customer = customer;
      this.restaurant = restaurant;
      this.orderValue = 0.0;
   }

   @Override
   public String toString() {
      return "Order{" +
              "customer=" + customer +
              ", restaurant=" + restaurant +
              '}';
   }
}

