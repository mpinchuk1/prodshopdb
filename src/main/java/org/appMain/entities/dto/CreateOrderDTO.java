package org.appMain.entities.dto;

import java.util.List;

public class CreateOrderDTO {
    private String customUser;
    private List<Long> products;
    private List<Integer> quantities;

    public CreateOrderDTO() {
    }

    public CreateOrderDTO(List<Long> products) {
        this.products = products;
    }

    public String getCustomUser() {
        return customUser;
    }

    public void setCustomer(String customUser) {
        this.customUser = customUser;
    }

    public List<Long> getProducts() {
        return products;
    }

    public void setProducts(List<Long> products) {
        this.products = products;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }
}
