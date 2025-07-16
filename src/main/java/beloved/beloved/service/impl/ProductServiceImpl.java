package beloved.beloved.service.impl;

import beloved.beloved.dto.ProductDto;
import beloved.beloved.entity.Category;
import beloved.beloved.service.ICategoryService;
import beloved.beloved.entity.Product;
import beloved.beloved.repository.ProductRepository;
import beloved.beloved.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ICategoryService categoryService;


    private Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setStock(productDto.getStock());

        if (productDto.getCategoryId() != null) {
            Category category = categoryService.getById(productDto.getCategoryId());
            product.setCategory(category);
        }


        return product;
    }

    private ProductDto entityToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setStock(product.getStock());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }
        return dto;
    }
    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product=dtoToEntity(productDto);
        Product savedProduct=productRepository.save(product);
        return entityToDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product=productRepository.findById(id).
                orElseThrow(()->new RuntimeException("Product not found"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setStock(productDto.getStock());
        if (productDto.getCategoryId() != null) {
            Category category = categoryService.getById(productDto.getCategoryId());
            product.setCategory(category);
        }

        Product updatedProduct=productRepository.save(product);
        return entityToDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)){
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> listProducts() {
        return productRepository.findAll().stream().
                map(product->entityToDto(product)).collect(Collectors.toList());
    }

}
