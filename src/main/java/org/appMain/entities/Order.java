package org.appMain.entities;


import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> orderedProduct;
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id")
    private Seller seller;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private Date orderDate;
    @Transient
    private final Calendar c = Calendar.getInstance();

    public Order() {
    }

    public Order(List<Product> orderedProduct, Seller seller, Customer customer) {
        this.orderedProduct = orderedProduct;
        this.seller = seller;
        this.customer = customer;
        this.price = countPrice(orderedProduct);
        this.orderDate = c.getTime();
    }

    public double countPrice(List<Product> products){
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public UUID getId() {
        return id;
    }

    public List<Product> getOrderedProduct() {
        return orderedProduct;
    }

    public Double getPrice() {
        return price;
    }

    public Seller getSeller() {
        return seller;
    }

    public Customer getCustomer() {
        return customer;
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
                ", seller=" + seller +
                ", customer=" + customer +
                ", orderDate=" + orderDate +
                ", c=" + c +
                '}';
    }
}
