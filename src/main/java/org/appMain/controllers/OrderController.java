package org.appMain.controllers;

import org.appMain.entities.CustomUser;
import org.appMain.entities.dto.CreateOrderDTO;
import org.appMain.entities.dto.OrderDTO;
import org.appMain.security.CustomUserDetails;
import org.appMain.services.OrderService;
import org.appMain.utils.NotEnoughQuantityException;
import org.appMain.utils.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("orders/create")
    public String createOrder(@ModelAttribute("createorder") CreateOrderDTO createOrder) {

        CustomUser customUser = getCurrentUser().getUser();
        logger.info("handling post request for createOrder from " + customUser.getLogin());

        if (createOrder.getProducts().size() < 1 || createOrder.getQuantities().size() < 1)
            return "redirect:/?ordererror";

        Map<Long, Integer> toOrder = createToOrderMap(createOrder.getProducts(), createOrder.getQuantities());

        try {
            orderService.addOrder(toOrder, customUser);
        } catch (NotEnoughQuantityException exception) {
            return "redirect:/?error";
        } catch (ProductNotFoundException exception) {
            return "redirect:/?notfound";
        }
        return "redirect:/myorders";
    }

    @PostMapping("orders/create_page")
    public String createOrderPage(@ModelAttribute("fororder") CreateOrderDTO createOrder,
                                  Model model) {
        if (createOrder.getProducts().size() < 1)
            return "redirect:/?selectprod";

        model.addAttribute("createorderproducts",
                orderService.createProdsToOrder(createOrder.getProducts()));
        model.addAttribute("createorder", new CreateOrderDTO());

        return "create-order";
    }

    @GetMapping("admin/orders")
    public String getAllOrders(Model model) {
        List<OrderDTO> orderDTOS = orderService.getAllOrdersWithProducts();
        logger.info("handling get request for ALL orders from ADMIN");
        model.addAttribute("orders", orderDTOS);
        return "orders";
    }

    @GetMapping("myorders")
    public String getMyOrders(Model model) {
        CustomUser user = getCurrentUser().getUser();
        List<OrderDTO> orderDTOS = orderService.getOrdersWithProductsForUser(user);
        logger.info("handling get request for myOrders from" + user.getLogin() + "\n Orders: " + orderDTOS);
        model.addAttribute("orders", orderDTOS);
        return "my-orders";
    }

    private Map<Long, Integer> createToOrderMap(List<Long> productsIds, List<Integer> quantities) {
        Map<Long, Integer> toOrder = new HashMap<>();
        for (int i = 0; i < productsIds.size(); i++) {
            toOrder.put(productsIds.get(i), quantities.get(i));
        }
        return toOrder;
    }

    private CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) SecurityContextHolder.
                getContext().
                getAuthentication().
                getPrincipal();
    }
}
