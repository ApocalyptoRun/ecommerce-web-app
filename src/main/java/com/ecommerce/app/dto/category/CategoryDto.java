package com.ecommerce.app.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto {
	private long id;
	private String categoryName;
	private String description;
	private String imgUrl;
}
