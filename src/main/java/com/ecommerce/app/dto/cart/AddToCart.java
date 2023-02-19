package com.ecommerce.app.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddToCart {
    private long id;
    private @NotNull int quantity;
    private @NotNull long productId;
}
