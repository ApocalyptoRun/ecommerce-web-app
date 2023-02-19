package com.ecommerce.app.service;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.dto.exceptions.ProductNotExistException;
import com.ecommerce.app.model.Category;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<ProductDto> listProduct() {
    	List<Product> products = productRepository.findAll();
    	List<ProductDto> productDtos = new ArrayList<>();
    	for(Product product : products) {
    		ProductDto productDto = new ProductDto();
    		productDto.setId(product.getId());
    		productDto.setCategoryId(product.getCategory().getCategoryId());
    		productDto.setDescription(product.getDescription());
    		productDto.setImageURL(product.getImageUrl());
    		productDto.setName(product.getName());
    		productDto.setPrice(product.getPrice());
    		productDtos.add(productDto);
    	}
    	
    	return productDtos;
    }

    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setImageUrl(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        productRepository.save(product);
    }

    public void updateProduct(Category category, ProductDto productDto) throws Exception{
        Optional<Product> optionalProduct = Optional.of(productRepository.getReferenceById(productDto.getId()));
        //throw an exception if product does not exist
        if(!optionalProduct.isPresent()){
            throw new Exception("product does not exist");
        }
        Product product = optionalProduct.get();
        product.setName(productDto.getName());
        product.setImageUrl(productDto.getImageURL());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        productRepository.save(product);
    }

    public ProductDto getProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageURL(product.getImageUrl());
        productDto.setCategoryId(product.getCategory().getCategoryId());
        return productDto;
    }

    public Product findProductById(long productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (Objects.isNull(optionalProduct)){
            throw new ProductNotExistException("product id is invalid ");
        }
        return optionalProduct.get();
    }

	public ProductDto getOne(long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		Product product = optionalProduct.get();
		ProductDto productDto = new ProductDto();
		
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageURL(product.getImageUrl());
        productDto.setCategoryId(product.getCategory().getCategoryId());
        
		return productDto;
	}

	public List<ProductDto> getProductsByCategory(Category category) {
		
		List<Product> products = productRepository.findAllByCategory(category);
		List<ProductDto> productDtos = new ArrayList<>();
		
		for(Product product : products) {
			ProductDto productDto = new ProductDto();
			productDto.setId(product.getId());
			productDto.setCategoryId(product.getCategory().getCategoryId());
			productDto.setDescription(product.getDescription());
			productDto.setImageURL(product.getImageUrl());
			productDto.setName(product.getName());
			productDto.setPrice(product.getPrice());
			productDtos.add(productDto);
		}
		
		return productDtos;
	}


}
