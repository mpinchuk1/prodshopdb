package org.appMain.entities;


import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private CustomUser customUser;
    private Date orderDate;
    @Transient
    private final Calendar c = Calendar.getInstance();

    public Order() {
    }

    public Order(Long id, List<OrderItem> orderItems, CustomUser customUser) {
        this.id = id;
        this.orderItems = orderItems;
        this.customUser = customUser;
        this.price = countPrice(orderItems);
        this.orderDate = c.getTime();
    }

    public double countPrice(List<OrderItem> orderItems) {
        return orderItems.stream().mapToDouble(orderItem -> orderItem.getProduct().getPrice()).sum();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
