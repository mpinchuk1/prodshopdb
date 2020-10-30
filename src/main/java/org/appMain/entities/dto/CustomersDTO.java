package org.appMain.entities.dto;


import org.appMain.entities.Customer;

import java.util.List;

public class CustomersDTO {
    private List<Customer> customers;

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
