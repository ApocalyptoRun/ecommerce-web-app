package com.ecommerce.app.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartDto {
    private List<CartItemDto> cartItemDtos;
    private double totalCost;
}
