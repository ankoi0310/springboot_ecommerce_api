package com.koi.ecommerce.service;

import com.koi.ecommerce.dao.OrderRepository;
import com.koi.ecommerce.dao.UserRepository;
import com.koi.ecommerce.domain.User;
import com.koi.ecommerce.dto.Purchase;
import com.koi.ecommerce.dto.PurchaseResponse;
import com.koi.ecommerce.entity.Order;
import com.koi.ecommerce.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    @Autowired
    public CheckoutServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // Retrieve the order info from DTO
        Order order = purchase.getOrder();

        // Generate tracking number using UUID (Universally Unique Identifier) ver 4
        String orderTrackingNumber = UUID.randomUUID().toString();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // Get OrderItems
        List<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        // Get Address
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // Get Customer
        User user = userRepository.findUserByEmail(purchase.getUser().getEmail());
        user.add(order);

        return new PurchaseResponse(orderTrackingNumber);
    }
}
