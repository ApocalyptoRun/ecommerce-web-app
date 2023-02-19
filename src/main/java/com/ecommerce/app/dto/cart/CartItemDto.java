package com.ecommerce.app.dto.cart;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.model.Cart;
import com.ecommerce.app.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemDto {
    private long id;
    private int quantity;
    private Product product;

    public CartItemDto(Cart cart) {
        this.id = cart.getId();
        this.quantity= cart.getQuantity();
        this.product = cart.getProduct();
    }
}
