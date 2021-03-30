package com.xav.pojo;

public class Customer {

    private Location customerLocation;
    private String customerId;

    public Location getCustomerLocation() {
        return customerLocation;
    }

    public void setCustomerLocation(Location customerLocation) {
        this.customerLocation = customerLocation;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Customer(Location customerLocation, String customerId) {
        this.customerLocation = customerLocation;
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (getCustomerLocation() != null ? !getCustomerLocation().equals(customer.getCustomerLocation()) : customer.getCustomerLocation() != null)
            return false;
        return getCustomerId() != null ? getCustomerId().equals(customer.getCustomerId()) : customer.getCustomerId() == null;
    }

    @Override
    public int hashCode() {
        int result = getCustomerLocation() != null ? getCustomerLocation().hashCode() : 0;
        result = 31 * result + (getCustomerId() != null ? getCustomerId().hashCode() : 0);
        return result;
    }
}
