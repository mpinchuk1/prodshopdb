package org.appMain.entities.dto;

import org.appMain.entities.Supplier;

import java.util.Date;

public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private Date deliveryDate;
    private Date expireDate;
    private Supplier deliveredBy;
    private int quantity;

    public ProductDTO(Long id, String name, Double price, Date deliveryDate, Date expireDate, Supplier deliveredBy, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.deliveryDate = deliveryDate;
        this.expireDate = expireDate;
        this.deliveredBy = deliveredBy;
        this.quantity = quantity;
    }

    public ProductDTO(Long id, String name, Double price, Date expireDate, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expireDate = expireDate;
        this.quantity = quantity;
    }

    public ProductDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Supplier getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(Supplier deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
