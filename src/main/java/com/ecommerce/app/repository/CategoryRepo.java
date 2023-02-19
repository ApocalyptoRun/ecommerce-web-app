package com.ecommerce.app.repository;

import com.ecommerce.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long>{

    Category findByCategoryName(String categoryName);
}

