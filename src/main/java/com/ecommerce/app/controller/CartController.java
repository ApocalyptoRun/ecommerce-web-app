package com.ecommerce.app.controller;


import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.cart.AddToCart;
import com.ecommerce.app.dto.cart.CartDto;
import com.ecommerce.app.model.Cart;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import com.ecommerce.app.service.AuthenficationService;
import com.ecommerce.app.service.CartService;
import com.ecommerce.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    AuthenficationService authenficationService;
    
    
    @GetMapping("/showCart")
    public String showCart() {
    	return "cart";
    }



    //post cart api

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addToCart(@ModelAttribute AddToCart addToCart, @CookieValue("token") String token){

        //Authenticate token
        authenficationService.authenticate(token);

        //Find the user
        User user = authenficationService.getUser(token);


        //Save the cart
        cartService.addToCart(addToCart, user);

        return "redirect:/product/showProduct/"+ addToCart.getProductId();
    }


    //get all cart item for a user

    @GetMapping("/list")	
    public String getCartItems(@CookieValue("token") String token, Model model){
        //authenticate the token
        authenficationService.authenticate(token);

        //Find the user
        User user = authenficationService.getUser(token);

        CartDto cartDto = cartService.listCartItem(user);
        model.addAttribute("cartDto", cartDto);
        return "cart";
    }

    //delete a cart item for a user

    @GetMapping("/delete/{cartItemId}")
    public String deleteCartItem(@PathVariable long cartItemId, @CookieValue("token") String token) throws Exception {
        //authenticate the token
        authenficationService.authenticate(token);

        //Find the user
        User user = authenficationService.getUser(token);

        cartService.deleteCartItem(cartItemId, user);

        return "redirect:/cart/list";
    }
}
