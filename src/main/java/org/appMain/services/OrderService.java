package org.appMain.services;

import org.appMain.entities.CustomUser;
import org.appMain.entities.Order;
import org.appMain.entities.Product;
import org.appMain.entities.Storage;
import org.appMain.repo.OrderRepository;
import org.appMain.repo.ProductRepository;
import org.appMain.repo.StorageRepository;
import org.appMain.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StorageRepository storageRepository;

    @Autowired
    public OrderService(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository, StorageRepository storageRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.storageRepository = storageRepository;
    }

    @Transactional
    public void addOrder(List<Product> requiredProducts, CustomUser customUser) {
        List<Product> toOrder = new ArrayList<>();

        for (Product p : requiredProducts) {
            Product product = productRepository.findProductByName(p.getName());
            Storage productItem = storageRepository.findByProduct(product);
            if (productItem == null) {
                continue;
            }
            int prodQuantity = productItem.getQuantity();
            if (prodQuantity > 0) {
                toOrder.add(product);
                int newQuantity = prodQuantity - 1;
                productItem.setQuantity(newQuantity);
                storageRepository.save(productItem);
            } else {
                System.out.println("There is no " + p.getName() + ". It has been sold already.");
                storageRepository.delete(productItem);
            }
        }

        if (toOrder.size() > 0) {
            Order order = new Order(toOrder, customUser);
            System.out.println("New order has created: " + order);
            userRepository.save(customUser);
            orderRepository.save(order);
        }
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
