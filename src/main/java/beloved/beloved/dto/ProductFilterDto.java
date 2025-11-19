package beloved.beloved.dto;

import java.util.List;

public class ProductFilterDto {
    private Long categoryId;
    private Boolean isPersonalized;
    private List<String> productTypes;

    public ProductFilterDto(Long categoryId, Boolean isPersonalized, List<String> productTypes) {
        this.categoryId = categoryId;
        this.isPersonalized = isPersonalized;
        this.productTypes = productTypes;
    }

    public ProductFilterDto() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getPersonalized() {
        return isPersonalized;
    }

    public void setPersonalized(Boolean personalized) {
        isPersonalized = personalized;
    }

    public List<String> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<String> productTypes) {
        this.productTypes = productTypes;
    }
}
