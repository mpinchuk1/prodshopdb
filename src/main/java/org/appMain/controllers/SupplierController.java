package org.appMain.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.appMain.entities.Product;
import org.appMain.entities.Supplier;
import org.appMain.entities.dto.DeliveryDTO;
import org.appMain.services.ProductService;
import org.appMain.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("supply")
public class SupplierController {
    private final SupplierService supplierService;
    private final ProductService productService;

    @Autowired
    public SupplierController(SupplierService supplierService, ProductService productService) {
        this.supplierService = supplierService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> deliverProducts(@RequestBody String deliverJson) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        DeliveryDTO deliver = gson.fromJson(deliverJson, DeliveryDTO.class);
        List<Product> toStorage = deliver.getProducts();
        List<Integer> prodQuantities = deliver.getProductQuantities();
        Supplier supplier = deliver.getCourier();

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        supplierService.addCourier(supplier);
        for (Product p : toStorage) {
            p.setDeliveryDate(currentDate);
            p.setDeliveredBy(supplier);
            productService.addProduct(p);
        }
        supplierService.addProductsToStorage(toStorage, prodQuantities);

        return ResponseEntity.ok().build();
    }
}
