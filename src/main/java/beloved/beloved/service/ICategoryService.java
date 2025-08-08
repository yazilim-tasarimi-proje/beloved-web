package beloved.beloved.service;

import beloved.beloved.dto.CategoryDto;
import beloved.beloved.entity.Category;

import java.util.List;

public interface ICategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    List<CategoryDto> listCategory();
    CategoryDto updateCategory(Long id,CategoryDto categoryDto);
    void deleteCategory(Long id);
    Category getById(Long id);
    CategoryDto addSubCategory(Long parentId, CategoryDto subCategoryDto);
    List<CategoryDto> listSubCategories(Long parentId);
    void deleteSubCategory(Long parentId, Long subCategoryId);
    List<Long> getAllSubCategoryIds(Long categoryId);
}
