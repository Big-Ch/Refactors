package com.gmail.v.c.charkin.gurmanfood.service.impl;

import com.gmail.v.c.charkin.gurmanfood.constants.ErrorMessage;
import com.gmail.v.c.charkin.gurmanfood.domain.Order;
import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;
import com.gmail.v.c.charkin.gurmanfood.domain.User;
import com.gmail.v.c.charkin.gurmanfood.dto.request.OrderRequest;
import com.gmail.v.c.charkin.gurmanfood.exception.EntityNotFoundException;
import com.gmail.v.c.charkin.gurmanfood.repository.OrderRepository;
import com.gmail.v.c.charkin.gurmanfood.service.DtoMapper;
import com.gmail.v.c.charkin.gurmanfood.service.OrderService;
import com.gmail.v.c.charkin.gurmanfood.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final DtoMapper dtoMapper;
    private final MailService mailService;

    @Override
    public Order getOrder(Long orderId) {
        User user = userService.getAuthenticatedUser();
        log.debug("Fetching order with ID: {} for user: {}", orderId, user.getEmail());
        return orderRepository.getByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> {
                    log.warn("Order not found with ID: {} for user: {}", orderId, user.getEmail());
                    return new EntityNotFoundException(ErrorMessage.ORDER_NOT_FOUND);
                });
    }

    @Override
    public List<Shawarma> getOrdering() {
        User user = userService.getAuthenticatedUser();
        return user.getShawarmaList();
    }

    @Override
    public Page<Order> getUserOrdersList(Pageable pageable) {
        User user = userService.getAuthenticatedUser();
        return orderRepository.findOrderByUserId(user.getId(), pageable);
    }

    @Override
    @Transactional
    public Long postOrder(User user, OrderRequest orderRequest) {
        log.info("Creating new order for user: {}", user.getEmail());
        Order order = dtoMapper.mapToOrder(orderRequest);
        order.setUser(user);
        order.getShawarmas().addAll(user.getShawarmaList());
        orderRepository.save(order);
        log.info("Order created successfully. Order ID: {}, User: {}, Total price: {}", 
                order.getId(), user.getEmail(), order.getTotalPrice());
        user.getShawarmaList().clear();
        log.debug("Cart cleared for user: {}", user.getEmail());
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("order", order);
        mailService.sendMessageHtml(order.getEmail(), "Order #" + order.getId(), "order-template", attributes);
        log.debug("Order confirmation email sent to: {}", order.getEmail());
        return order.getId();
    }
}
