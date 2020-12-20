package org.appMain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String lastName;
    private String supplierCompanyName;
    @OneToMany(mappedBy = "deliveredBy")
    private List<Product> deliveryProducts;

    public Courier() {
    }

    public Courier(String lastName, String supplierCompanyName) {
        this.lastName = lastName;
        this.supplierCompanyName = supplierCompanyName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSupplierCompanyName() {
        return supplierCompanyName;
    }

    public List<Product> getDeliveryProducts() {
        return deliveryProducts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSupplierCompanyName(String supplierCompanyName) {
        this.supplierCompanyName = supplierCompanyName;
    }

    public void setDeliveryProducts(List<Product> deliveryProducts) {
        this.deliveryProducts = deliveryProducts;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", supplierCompanyName='" + supplierCompanyName + '\'' +
                ", deliveryProducts=" + deliveryProducts +
                '}';
    }
}
