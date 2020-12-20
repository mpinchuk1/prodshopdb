package org.appMain.entities.dto;

import org.appMain.entities.Product;

import java.util.List;

public class CreateOrderDTO {
    private String customUser;
    private List<Product> products;

    public String getCustomUser() {
        return customUser;
    }

    public void setCustomer(String customUser) {
        this.customUser = customUser;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
