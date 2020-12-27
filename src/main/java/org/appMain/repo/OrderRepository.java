package org.appMain.repo;

import org.appMain.entities.CustomUser;
import org.appMain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByCustomUser(CustomUser user);
}
