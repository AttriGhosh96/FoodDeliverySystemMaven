package com.xav.receivedData;

import com.xav.pojo.Customer;
import com.xav.pojo.Restaurant;

public class Order {
   private Customer customer;
   private Restaurant restaurant;

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

   public Order(Customer customer, Restaurant restaurant) {
      this.customer = customer;
      this.restaurant = restaurant;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Order)) return false;

      Order order = (Order) o;

      if (getCustomer() != null ? !getCustomer().equals(order.getCustomer()) : order.getCustomer() != null)
         return false;
      return getRestaurant() != null ? getRestaurant().equals(order.getRestaurant()) : order.getRestaurant() == null;
   }

   @Override
   public int hashCode() {
      int result = getCustomer() != null ? getCustomer().hashCode() : 0;
      result = 31 * result + (getRestaurant() != null ? getRestaurant().hashCode() : 0);
      return result;
   }

   @Override
   public String toString() {
      return "Order{" +
              "customer=" + customer +
              ", restaurant=" + restaurant +
              '}';
   }
}

