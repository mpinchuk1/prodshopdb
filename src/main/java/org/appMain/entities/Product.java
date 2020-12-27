package org.appMain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double price;
    @JsonFormat(pattern = "MMM dd, yyyy")
    private Date deliveryDate;
    @JsonFormat(pattern = "MMM dd, yyyy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date expireDate;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier deliveredBy;


    public Product() {
    }

    public Product(String name, Double price, Date expireDate, Supplier deliveredBy) {
        this.name = name;
        this.price = price;
        this.deliveryDate = new Date();
        this.expireDate = expireDate;
        this.deliveredBy = deliveredBy;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public Supplier getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(Supplier deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getPrice(), product.getPrice()) &&
                Objects.equals(deliveryDate, product.deliveryDate) &&
                Objects.equals(getExpireDate(), product.getExpireDate()) &&
                Objects.equals(getDeliveredBy(), product.getDeliveredBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(),
                deliveryDate, getExpireDate(), getDeliveredBy());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", deliveryDate=" + deliveryDate +
                ", expireDate=" + expireDate +
                '}';
    }

}
