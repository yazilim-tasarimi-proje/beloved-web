package beloved.beloved.service;

import beloved.beloved.dto.ProductDto;
import beloved.beloved.entity.Product;

import java.util.List;

public interface IProductService {
    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(Long id,ProductDto productDto);
    void deleteProduct(Long id);
    List<ProductDto> listProducts();
}
