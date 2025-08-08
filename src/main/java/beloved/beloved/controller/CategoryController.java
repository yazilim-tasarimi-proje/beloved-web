package beloved.beloved.controller;

import beloved.beloved.dto.CategoryDto;
import beloved.beloved.entity.Category;
import beloved.beloved.service.ICategoryService;
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
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/list")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.listCategory());
    }
    @PostMapping("/add")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto addedCategory = categoryService.addCategory(categoryDto);
        return ResponseEntity.ok(addedCategory);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        try{
           CategoryDto updateCategory = categoryService.updateCategory(id, categoryDto);
            return ResponseEntity.ok(updateCategory);
        }catch(RuntimeException e){
            return ResponseEntity.status(404).body(null);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category deleted successfully");
        }catch(RuntimeException e){
            return ResponseEntity.status(404).body("Category not found");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(category);
    }
    @PostMapping("/{parentId}/subcategories/add")
    public ResponseEntity<CategoryDto> addSubCategory(@PathVariable Long parentId, @RequestBody CategoryDto subCategoryDto) {
        return ResponseEntity.ok(categoryService.addSubCategory(parentId, subCategoryDto));
    }

    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<List<CategoryDto>> listSubCategories(@PathVariable Long parentId) {
        return ResponseEntity.ok(categoryService.listSubCategories(parentId));
    }

    @DeleteMapping("/{parentId}/subcategories/{subCategoryId}")
    public ResponseEntity<String> deleteSubCategory(@PathVariable Long parentId, @PathVariable Long subCategoryId) {
        categoryService.deleteSubCategory(parentId, subCategoryId);
        return ResponseEntity.ok("Subcategory deleted successfully");
    }



}
