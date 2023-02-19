package com.ecommerce.app.controller;


import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.dto.cart.AddToCart;
import com.ecommerce.app.model.Category;

import com.ecommerce.app.repository.CategoryRepo;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;
    
    @GetMapping("/showFormAddProduct")
    public String formAddProduct(Model model) {
    	ProductDto productDto = new ProductDto();
    	model.addAttribute("productDto", productDto);
    	return "formAddProduct";
    }
    
    @GetMapping("/showFormEditProduct/{id}")
    public String formEditProduct(@PathVariable("id") long id, Model model) {
    	ProductDto productDto = productService.getOne(id);
    	model.addAttribute("productDto", productDto);
    	return"formEditProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute ProductDto productDto){
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
       /* if(!optionalCategory.isPresent()){
            return null;
        } */
        Category category = optionalCategory.get();
        System.out.println(category);
        productService.createProduct(productDto, category);
        return "redirect:/product/list";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        /*if(!optionalCategory.isPresent()){           
        }*/
        Category category = optionalCategory.get();
        productService.updateProduct(category, productDto);
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String listProduct(Model model){
    	List<ProductDto> productDtos =  productService.listProduct();
    	model.addAttribute("productDtos", productDtos);
        return "listProducts";
    }
    
    @GetMapping("/ByCategory/{id}")
    public String getProductsByCategory(@PathVariable long id, Model model) {
    	Optional<Category> optionalCategory = categoryRepo.findById(id);
    	Category category = optionalCategory.get();
    	List<ProductDto> productDtos = productService.getProductsByCategory(category); 
    	model.addAttribute("productDtos", productDtos);
    	return "home";
    }
    
    @GetMapping("/showProduct/{id}")
    public String showProduct(@PathVariable("id") long id, Model model) {
    	ProductDto productDto = productService.getOne(id);
    	model.addAttribute("productDto",productDto);
    	
    	AddToCart addToCart = new AddToCart();
    	model.addAttribute("addToCart", addToCart);
    	return "product";
    }
    
  
}
















