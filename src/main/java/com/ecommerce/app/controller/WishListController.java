package com.ecommerce.app.controller;

import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.model.Category;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import com.ecommerce.app.model.WishList;
import com.ecommerce.app.repository.CategoryRepo;
import com.ecommerce.app.service.AuthenficationService;
import com.ecommerce.app.service.WishListService;
import com.google.common.base.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenficationService authenficationService;
    
    @Autowired
    CategoryRepo categoryRepo;
        

    //Save product as wishList Item

    @PostMapping("/add")
    public String addToWishList(@ModelAttribute ProductDto productDto, @CookieValue("token") String token){
    	
    	System.out.println(token);
    	
    	java.util.Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
    	Category category = optionalCategory.get();
    	
    	Product product = wishListService.convert(productDto, category);

        //Authenticate token //@CookieValue(value = "username")
        authenficationService.authenticate(token);

        //find the User
        User user = authenficationService.getUser(token);

        //Save the item in the wishList
        WishList wishList = new WishList();
        wishList.setProduct(product);
        wishList.setUser(user);
        wishList.setCreatedDate(new Date());

        wishListService.createWishList(wishList);

        return "redirect:/product/showProduct/"+productDto.getId();
    }

    //Get all wishlist item from a user

    @GetMapping("/list")
    public String getWishlist(@CookieValue("token") String token, Model model){
    	
        //Authenticate token
        authenficationService.authenticate(token);

        //find the User
        User user = authenficationService.getUser(token);

        List<ProductDto> productDtos = wishListService.getWishlistForUser(user);
        model.addAttribute("productDtos", productDtos);

        return "wishlist";
        
    	
    }
    
    // delete a product from wishlist
    
    @GetMapping("/delete/{id}")
    public String deleteWishList(@PathVariable("id") long id) {
    	System.out.println(id);
    	wishListService.deleteWishList(id);
		
		return "redirect:/wishlist/list";
	}
}













