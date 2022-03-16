package com.koi.ecommerce.service;

import com.koi.ecommerce.dto.Purchase;
import com.koi.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
