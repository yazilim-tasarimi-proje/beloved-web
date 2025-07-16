package beloved.beloved.service.impl;

import beloved.beloved.dto.CategoryDto;
import beloved.beloved.entity.Category;
import beloved.beloved.repository.CategoryRepository;
import beloved.beloved.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category dtoToEntity(CategoryDto dto){
        Category category= new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;
    }

    private CategoryDto entityToDto(Category entity){
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setId(entity.getId());
        categoryDto.setName(entity.getName());
        return categoryDto;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category=dtoToEntity(categoryDto);
        Category savedCategory=categoryRepository.save(category);
        return entityToDto(savedCategory);
    }

    @Override
    public List<CategoryDto> listCategory() {
        return categoryRepository.findAll().stream()
                .map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long id,CategoryDto categoryDto) {
        Category category= categoryRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDto.getName());
        Category updatedCategory=categoryRepository.save(category);
        return entityToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)){
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }


}
