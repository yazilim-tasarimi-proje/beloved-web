package beloved.beloved.service.impl;

import beloved.beloved.dto.ProductDto;
import beloved.beloved.dto.ProductFilterDto;
import beloved.beloved.entity.Category;
import beloved.beloved.entity.Product;
import beloved.beloved.repository.ProductRepository;
import beloved.beloved.service.ICategoryService;
import beloved.beloved.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

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

        product.setPersonalized(productDto.getPersonalized() != null ? productDto.getPersonalized() : false);
        product.setProductType(productDto.getProductType());

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

        dto.setPersonalized(product.isPersonalized());
        dto.setProductType(product.getProductType());

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }
        return dto;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = dtoToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return entityToDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setStock(productDto.getStock());

        product.setPersonalized(productDto.getPersonalized() != null ? productDto.getPersonalized() : false);
        product.setProductType(productDto.getProductType());

        if (productDto.getCategoryId() != null) {
            Category category = categoryService.getById(productDto.getCategoryId());
            product.setCategory(category);
        }

        Product updatedProduct = productRepository.save(product);
        return entityToDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> listProducts() {
        return productRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> filterProducts(ProductFilterDto filterDto) {
        Specification<Product> spec = (root, query, cb) -> cb.conjunction();

        if (filterDto.getCategoryId() != null) {
            List<Long> allCategoryIds = categoryService.getAllSubCategoryIds(filterDto.getCategoryId());
            spec = spec.and((root, query, cb) ->
                    root.get("category").get("id").in(allCategoryIds));
        }

        if (filterDto.getPersonalized() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("personalized"), filterDto.getPersonalized()));
        }

        if (filterDto.getProductTypes() != null && !filterDto.getProductTypes().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    root.get("productType").in(filterDto.getProductTypes()));
        }

        List<Product> products = productRepository.findAll(spec);
        return products.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
