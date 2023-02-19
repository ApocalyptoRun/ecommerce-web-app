package com.ecommerce.app.controller;

import com.ecommerce.app.model.Category;
import com.ecommerce.app.service.CategoryService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    
    
    @RequestMapping(value = "/showFormAddCategory")
    public String addCatogory(Model model) {
    	Category category = new Category();
    	model.addAttribute("category", category);
    	return "formAddCategory";
    }
    
    @RequestMapping(value ="/showFormEditCategory/{Id}")
    public String editCatogory(@PathVariable("Id") long id, Model model) {
    	Optional<Category> category = categoryService.getOne(id);
    	model.addAttribute("category", category);
    	return "formEditCategory";
    }    

    @RequestMapping(value = "/category/create", method = RequestMethod.POST)
    public String createCategory(@ModelAttribute("category") Category category, Model model){
        categoryService.createCategory(category);
        model.addAttribute("test", true);
        model.addAttribute("categoryName", category.getCategoryName());
        return "redirect:/category/list";
    }
    

    @RequestMapping(value="/category/list", method = RequestMethod.GET)
    public String listCategory(Model model){
    	List<Category> categories = categoryService.listCategory();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @RequestMapping(value="/category/update", method = RequestMethod.POST)
    public String updateCategory(@ModelAttribute Category category){
        categoryService.editCategory(category.getCategoryId(), category);
        return "redirect:/category/list";
    }
}
