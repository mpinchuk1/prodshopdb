package org.appMain.entities;


import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> orderedProduct;
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private CustomUser customUser;
    private Date orderDate;
    @Transient
    private final Calendar c = Calendar.getInstance();

    public Order() {
    }

    public Order(List<Product> orderedProduct, CustomUser customUser) {
        this.orderedProduct = orderedProduct;
        this.customUser = customUser;
        this.price = countPrice(orderedProduct);
        this.orderDate = c.getTime();
    }

    public double countPrice(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public Long getId() {
        return id;
    }

    public List<Product> getOrderedProduct() {
        return orderedProduct;
    }

    public Double getPrice() {
        return price;
    }

    public CustomUser getCustomer() {
        return customUser;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", product=" + orderedProduct +
                ", price=" + price +
                ", customer=" + customUser +
                ", orderDate=" + orderDate +
                ", c=" + c +
                '}';
    }
}
