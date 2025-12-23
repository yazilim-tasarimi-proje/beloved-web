package beloved.beloved.service.impl;

import beloved.beloved.dto.CategoryDto;
import beloved.beloved.entity.Category;
import beloved.beloved.repository.CategoryRepository;
import beloved.beloved.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category dtoToEntity(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());

        if (dto.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParent(parentCategory);
        }

        return category;
    }

    private CategoryDto entityToDto(Category entity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(entity.getId());
        categoryDto.setName(entity.getName());

        if (entity.getParent() != null) {
            categoryDto.setParentId(entity.getParent().getId());
        }

        return categoryDto;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = dtoToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return entityToDto(savedCategory);
    }

    @Override
    public List<CategoryDto> listCategory() {
        return categoryRepository.findByParentIsNull().stream()
                .map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDto.getName());

        if (categoryDto.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParent(parentCategory);
        } else {
            category.setParent(null);
        }

        Category updatedCategory = categoryRepository.save(category);
        return entityToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public CategoryDto addSubCategory(Long parentId, CategoryDto subCategoryDto) {
        Category parentCategory = categoryRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent category not found"));
        Category subCategory = dtoToEntity(subCategoryDto);
        subCategory.setParent(parentCategory);
        Category savedSubCategory = categoryRepository.save(subCategory);
        return entityToDto(savedSubCategory);
    }

    @Override
    public List<CategoryDto> listSubCategories(Long parentId) {
        return categoryRepository.findByParent_Id(parentId).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSubCategory(Long parentId, Long subCategoryId) {
        Category parentCategory = categoryRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent category not found"));

        Category subCategory = categoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));

        if (!subCategory.getParent().equals(parentCategory)) {
            throw new RuntimeException("This subcategory does not belong to the specified parent");
        }
        categoryRepository.delete(subCategory);
    }

    // Tüm alt kategorilerin ID'lerini döner (recursive)
    @Override
    public List<Long> getAllSubCategoryIds(Long categoryId) {
        List<Long> result = new ArrayList<>();
        result.add(categoryId); // Ana kategori id'si de dahil
        addChildCategoryIds(categoryId, result);
        return result;
    }

    // Recursive alt kategori ID ekleme fonksiyonu
    private void addChildCategoryIds(Long parentId, List<Long> categoryIds) {
        List<Category> childCategories = categoryRepository.findByParent_Id(parentId);
        for (Category child : childCategories) {
            categoryIds.add(child.getId());
            addChildCategoryIds(child.getId(), categoryIds);
        }
    }
}
