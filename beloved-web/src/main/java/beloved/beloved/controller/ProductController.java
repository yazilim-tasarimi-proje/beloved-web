package beloved.beloved.controller;

import beloved.beloved.dto.ProductDto;
import beloved.beloved.dto.ProductFilterDto;
import beloved.beloved.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        ProductDto addedProduct = productService.addProduct(productDto);
        return ResponseEntity.ok(addedProduct);
    }
    @GetMapping("/list")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.listProducts());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
       try{
          ProductDto updateProduct= productService.updateProduct(id, productDto);
           return ResponseEntity.ok(updateProduct);
       }catch(RuntimeException e){
           return ResponseEntity.status(404).body(null);
       }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try{
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        }catch(RuntimeException e){
            return ResponseEntity.status(404).body("Product not found");
        }
    }
    @PostMapping("/filter")
    public ResponseEntity<List<ProductDto>> filterProducts(@RequestBody ProductFilterDto filterDto) {
        List<ProductDto> filteredProducts = productService.filterProducts(filterDto);
        return ResponseEntity.ok(filteredProducts);
    }


}
