package org.appMain.controllers;

import org.appMain.entities.CustomUser;
import org.appMain.entities.Order;
import org.appMain.entities.Product;
import org.appMain.entities.dto.CreateOrderDTO;
import org.appMain.services.OrderService;
import org.appMain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderDTO createOrder) {
        String customUserLogin = createOrder.getCustomUser();
        CustomUser customUser = userService.findByLogin(customUserLogin);
        List<Product> products = createOrder.getProducts();
        orderService.addOrder(products, customUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Order> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return orders;
    }
}
