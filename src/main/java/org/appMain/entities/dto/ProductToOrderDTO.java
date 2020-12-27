package org.appMain.entities.dto;

public class ProductToOrderDTO {
    private String name;
    private int quantity;

    public ProductToOrderDTO() {
    }

    public ProductToOrderDTO(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
