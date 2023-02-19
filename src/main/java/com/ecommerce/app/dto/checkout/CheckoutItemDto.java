package com.ecommerce.app.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckoutItemDto {
    private String productName;
    private int quantity;
    private double price;
    private long productId;
    private long userId;
}
