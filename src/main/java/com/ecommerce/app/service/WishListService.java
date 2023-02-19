package com.ecommerce.app.service;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.model.Category;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import com.ecommerce.app.model.WishList;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;
    public void createWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<ProductDto> getWishlistForUser(User user) {
        final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();
        for (WishList wishList : wishLists){
            productDtos.add(productService.getProductDto(wishList.getProduct()));
        }

        return productDtos;
    }

	public Product convert(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setImageUrl(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
		return product;
	}

	public void deleteWishList(long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		Product product = optionalProduct.get();
		System.out.println(product);
		//wishListRepository.deleteByProduct(product);
		wishListRepository.deleteByProduct(product);
	}

	


}
