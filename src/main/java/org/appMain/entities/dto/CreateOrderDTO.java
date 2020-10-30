package org.appMain.entities.dto;

import org.appMain.entities.Customer;
import org.appMain.entities.Product;
import org.appMain.entities.Seller;

import java.util.List;

public class CreateOrderDTO {
    private Seller seller;
    private Customer customer;
    private List<Product> products;

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
