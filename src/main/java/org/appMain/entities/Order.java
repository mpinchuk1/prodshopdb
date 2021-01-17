package org.appMain.entities;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id")
    private CustomUser customUser;
    private Date orderDate;
    @Transient
    private final Calendar c = Calendar.getInstance();

    public Order() {
    }

    public Order(List<OrderItem> orderItems, CustomUser customUser) {

        this.orderItems = orderItems;
        this.customUser = customUser;
        this.price = countPrice(orderItems);
        this.orderDate = c.getTime();
    }

    public BigDecimal countPrice(List<OrderItem> orderItems) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (OrderItem or : orderItems) {
            totalPrice = totalPrice.add(or.getProduct().getPrice());
        }
        return totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", customer=" + customUser +
                ", orderDate=" + orderDate +
                '}';
    }
}
