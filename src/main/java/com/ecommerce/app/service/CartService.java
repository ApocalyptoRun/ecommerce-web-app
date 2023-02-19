package com.ecommerce.app.service;

import com.ecommerce.app.dto.cart.AddToCart;
import com.ecommerce.app.dto.cart.CartDto;
import com.ecommerce.app.dto.cart.CartItemDto;
import com.ecommerce.app.dto.exceptions.CustomException;
import com.ecommerce.app.model.Cart;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import com.ecommerce.app.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;



    public void addToCart(AddToCart addToCart, User user) {
        //Find the product by ID
        Product product  = productService.findProductById(addToCart.getProductId());

        //Create a new cart
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(addToCart.getQuantity());
        cart.setCreatedDate(new Date());

        cartRepository.save(cart);

    }


    public CartDto listCartItem(User user) {
        final List<Cart> cartList = cartRepository.findByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItemDtos = new ArrayList<>();
        double totalCost = 0;

        for (Cart cart : cartList){
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cart.getQuantity() * cart.getProduct().getPrice();
            cartItemDtos.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setCartItemDtos(cartItemDtos);
        cartDto.setTotalCost(totalCost);

        return cartDto;
    }


    public void deleteCartItem(long cartItemId, User user) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if (optionalCart.isEmpty()){
            throw new CustomException("cart not valid");
        }

       Cart cart = optionalCart.get();

       if (cart.getUser() != user){
           throw new CustomException("user is not valid");
       }

       cartRepository.delete(cart);

    }
}
