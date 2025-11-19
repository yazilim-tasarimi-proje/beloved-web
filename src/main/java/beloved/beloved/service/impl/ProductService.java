package beloved.beloved.service.impl;

import beloved.beloved.dto.ProductDto;
import beloved.beloved.dto.ProductFilterDto;
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

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = ProductFactory.dtoToEntity(productDto, categoryService);
        Product savedProduct = productRepository.save(product);
        return ProductFactory.entityToDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductFactory.updateEntityFromDto(product, productDto, categoryService);

        Product updatedProduct = productRepository.save(product);
        return ProductFactory.entityToDto(updatedProduct);
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
                .map(ProductFactory::entityToDto)
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
        return products.stream().map(ProductFactory::entityToDto).collect(Collectors.toList());
    }
}
