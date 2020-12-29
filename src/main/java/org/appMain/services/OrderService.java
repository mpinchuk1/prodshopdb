package org.appMain.services;

import org.appMain.entities.*;
import org.appMain.entities.dto.OrderDTO;
import org.appMain.entities.dto.ProductToOrderDTO;
import org.appMain.repo.OrderItemRepository;
import org.appMain.repo.OrderRepository;
import org.appMain.repo.ProductRepository;
import org.appMain.repo.StorageRepository;
import org.appMain.utils.NotEnoughQuantityException;
import org.appMain.utils.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void addOrder(Map<Long, Integer> toOrderMap, CustomUser customUser) throws NotEnoughQuantityException, ProductNotFoundException {

        List<OrderItem> toOrder = new ArrayList<>();

        for (Map.Entry<Long, Integer> p : toOrderMap.entrySet()) {
            Long productId = p.getKey();
            int productQty = p.getValue();

            Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
            Storage productItem = storageRepository.findByProductName(product.getName()).orElse(null);
            if (productItem == null) {
                continue;
            }
            saveNewQuantity(product, productItem, productQty, toOrder);
        }

        saveOrder(toOrder, customUser);
    }

    @Transactional
    public List<OrderDTO> getOrdersWithProductsForUser(CustomUser user) {
        return convertToDto(orderRepository.findOrdersByCustomUser(user));
    }

    @Transactional
    public List<OrderDTO> getAllOrdersWithProducts() {
        return convertToDto(orderRepository.findAll());
    }

    private List<OrderDTO> convertToDto(List<Order> orders) {
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            List<OrderItem> orderItems = order.getOrderItems();

            for (OrderItem orderItem : orderItems) {
                ProductToOrderDTO productToOrderDTO = new ProductToOrderDTO(orderItem.getProduct().getName(), orderItem.getQuantity());
                orderDTO.getOrderedProducts().add(productToOrderDTO);
            }
            orderDTO.setId(order.getId());
            orderDTO.setCustomerId(order.getCustomUser().getId());
            orderDTO.setDateCreated(order.getOrderDate());
            orderDTO.setPrice(order.getPrice());

            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    private void saveNewQuantity(Product product, Storage productItem, int requiredQty, List<OrderItem> toOrder)
            throws NotEnoughQuantityException {
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
            throw new NotEnoughQuantityException();
        }
    }

    private void saveOrder(List<OrderItem> toOrder, CustomUser customUser) {
        if (toOrder.size() > 0) {


            Order order = new Order(toOrder, customUser);
            for (OrderItem orderItem : toOrder) {
                orderItem.setOrder(order);
                orderItemRepository.save(orderItem);
            }
            order.setOrderItems(toOrder);
            order.setCustomUser(customUser);
            orderRepository.save(order);

            logger.info("New order has created: " + order);
        }
    }
}
