package org.appMain.services;

import org.appMain.entities.*;
import org.appMain.repo.OrderItemRepository;
import org.appMain.repo.OrderRepository;
import org.appMain.repo.ProductRepository;
import org.appMain.repo.StorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final StorageRepository storageRepository;

    @Autowired
    public OrderService(ProductRepository productRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, StorageRepository storageRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.storageRepository = storageRepository;
    }

    @Transactional
    public void addOrder(List<Product> requiredProducts, CustomUser customUser) {
        logger.info("Trying to create new order for " + customUser.getLogin());

        List<OrderItem> toOrder = new ArrayList<>();
        Map<String, Long> integerMap = requiredProducts.stream().collect(
                Collectors.groupingBy(Product::getName, Collectors.counting()));
        System.out.println(integerMap);
        for (Map.Entry<String, Long> p : integerMap.entrySet()) {
            String productName = p.getKey();
            long productQty = p.getValue();
            Product product = productRepository.findProductByName(productName);
            Storage productItem = storageRepository.findByProduct(product);
            if (productItem == null) {
                continue;
            }
            saveNewQuantity(product, productItem, (int) productQty, toOrder);
        }

        if (toOrder.size() > 0) {
            long orderId = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);

            Order order = new Order(orderId, toOrder, customUser);
            toOrder.forEach(orderItem -> {
                orderItem.setOrder(order);
                orderItemRepository.save(orderItem);
            });
            order.setOrderItems(toOrder);
            order.setCustomUser(customUser);
            orderRepository.save(order);

            logger.info("New order has created: " + order);
        }
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    private void saveNewQuantity(Product product, Storage productItem, int requiredQty, List<OrderItem> toOrder) {
        int prodQuantity = productItem.getQuantity();

        if (prodQuantity >= requiredQty) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(requiredQty);
            toOrder.add(orderItem);
            int newQuantity = prodQuantity - requiredQty;
            productItem.setQuantity(newQuantity);
            storageRepository.save(productItem);
        } else {
            logger.info("Not enough " + product.getName() + ". Need " + requiredQty + ", but there is only " + prodQuantity);
        }
    }
}
