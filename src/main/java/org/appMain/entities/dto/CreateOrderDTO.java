package org.appMain.entities.dto;

import org.appMain.entities.CustomUser;
import org.appMain.entities.Product;

import java.util.List;

public class CreateOrderDTO {
    private CustomUser customUser;
    private List<Product> products;

    public CustomUser getCustomer() {
        return customUser;
    }

    public void setCustomer(CustomUser customUser) {
        this.customUser = customUser;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
