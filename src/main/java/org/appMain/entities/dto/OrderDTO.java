package org.appMain.entities.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDTO {

    private Long id;
    private Long customerId;
    private Date dateCreated;
    private BigDecimal price;
    private List<ProductToOrderDTO> orderedProducts;

    public OrderDTO() {
        this.orderedProducts = new ArrayList<>();
    }

    public OrderDTO(Long id, Long customerId, Date dateCreated, BigDecimal price, List<ProductToOrderDTO> orderedProducts) {
        this.id = id;
        this.customerId = customerId;
        this.dateCreated = dateCreated;
        this.price = price;
        this.orderedProducts = orderedProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ProductToOrderDTO> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<ProductToOrderDTO> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", dateCreated=" + dateCreated +
                ", price=" + price +
                ", orderedProducts=" + orderedProducts +
                '}';
    }
}
