package com.ecommerce.app.service;

import com.ecommerce.app.model.Category;
import com.ecommerce.app.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    public void createCategory(Category category){
        categoryRepo.save(category);
    }

    public List<Category> listCategory(){
        return categoryRepo.findAll();
    }

    public void editCategory(long id, Category updateCategory){
        Category category = categoryRepo.getReferenceById(id);
        category.setCategoryName(updateCategory.getCategoryName());
        category.setDescription(updateCategory.getDescription());
        category.setImgUrl(updateCategory.getImgUrl());
        categoryRepo.save(category);
    }

    public Category readCategory(long categoryName) {
        return categoryRepo.findByCategoryName(String.valueOf(categoryName));
    }

	public Optional<Category> getOne(long id) {
		return Optional.of(categoryRepo.getReferenceById(id));
	}

}
