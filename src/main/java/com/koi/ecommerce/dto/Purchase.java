package com.koi.ecommerce.dto;

import com.koi.ecommerce.domain.User;
import com.koi.ecommerce.entity.Address;
import com.koi.ecommerce.entity.Order;
import com.koi.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class Purchase {
    private User user;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private List<OrderItem> orderItems;
}
