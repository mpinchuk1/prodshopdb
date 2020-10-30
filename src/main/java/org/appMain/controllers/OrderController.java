package org.appMain.controllers;

import org.appMain.entities.Customer;
import org.appMain.entities.Product;
import org.appMain.entities.Seller;
import org.appMain.entities.dto.CreateOrderDTO;
import org.appMain.entities.dto.OrdersDTO;
import org.appMain.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderDTO createOrder){
        Seller seller = createOrder.getSeller();
        Customer customer = createOrder.getCustomer();
        List<Product> products = createOrder.getProducts();
        orderService.addOrder(seller, products, customer);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public @ResponseBody OrdersDTO getAllOrders(){
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setOrders(orderService.getAllOrders());
        return ordersDTO;
    }
}
