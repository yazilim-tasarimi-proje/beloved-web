package beloved.beloved.service.impl.factory;

import beloved.beloved.dto.ProductDto;
import beloved.beloved.entity.Category;
import beloved.beloved.entity.Product;
import beloved.beloved.service.ICategoryService;


 // Basit factory sınıfı - Product DTO/entity dönüşümlerini merkezileştirir.
 // Statik metotlar ProductService içinde kullanılmak üzere tasarlandı.

public class ProductFactory {

    public static Product dtoToEntity(ProductDto dto, ICategoryService categoryService) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setStock(dto.getStock());

        product.setPersonalized(dto.getPersonalized() != null ? dto.getPersonalized() : false);
        product.setProductType(dto.getProductType());

        if (dto.getCategoryId() != null && categoryService != null) {
            Category category = categoryService.getById(dto.getCategoryId());
            product.setCategory(category);
        }

        return product;
    }

    public static void updateEntityFromDto(Product product, ProductDto dto, ICategoryService categoryService) {
        if (product == null || dto == null) return;

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setStock(dto.getStock());

        product.setPersonalized(dto.getPersonalized() != null ? dto.getPersonalized() : false);
        product.setProductType(dto.getProductType());

        if (dto.getCategoryId() != null && categoryService != null) {
            Category category = categoryService.getById(dto.getCategoryId());
            product.setCategory(category);
        }
    }

    public static ProductDto entityToDto(Product product) {
        if (product == null) return null;

        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setStock(product.getStock());

        dto.setPersonalized(product.isPersonalized());
        dto.setProductType(product.getProductType());

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }
        return dto;
    }
}

