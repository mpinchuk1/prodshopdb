package org.appMain.entities.dto;

import org.appMain.entities.Product;
import org.appMain.entities.Supplier;

import java.util.List;

public class DeliveryDTO {
    private List<Product> products;
    private List<Integer> productQuantities;
    private Supplier supplier;

    public DeliveryDTO() {
    }

    public DeliveryDTO(List<Product> products, List<Integer> productQuantities, Supplier supplier) {
        this.products = products;
        this.productQuantities = productQuantities;
        this.supplier = supplier;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(List<Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }

    public Supplier getCourier() {
        return supplier;
    }

    public void setCourier(Supplier supplier) {
        this.supplier = supplier;
    }
}
