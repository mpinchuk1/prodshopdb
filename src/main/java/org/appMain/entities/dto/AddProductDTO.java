package org.appMain.entities.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AddProductDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private Date expireDate;
    private String deliveredBy;
    private int quantity;

    public AddProductDTO(Long id, String name, BigDecimal price, Date expireDate, String deliveredBy, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expireDate = expireDate;
        this.deliveredBy = deliveredBy;
        this.quantity = quantity;
    }

    public AddProductDTO() {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "AddProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", expireDate=" + expireDate +
                ", deliveredBy='" + deliveredBy + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
