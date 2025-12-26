package beloved.beloved.dto;

import beloved.beloved.entity.Category;

import java.math.BigDecimal;
import java.util.Set;

public class ProductDto {
   private Long id;
    private String name;
    private BigDecimal price;
    private int stock;
    private String description;
    private String imageUrl;
    private Long  categoryId;
    private Boolean isPersonalized;
    private String productType;


    private java.util.Set<Long> suitableSpecialDayIds;
    private java.util.Set<Long> suitableRelationTypeIds;
    private String color;
    private Integer minAge;
    private Integer maxAge;


    public ProductDto(Long id, String name, BigDecimal price, int stock, String description, String imageUrl, Long categoryId, Boolean isPersonalized, String productType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.isPersonalized = isPersonalized;
        this.productType = productType;

    }

    public ProductDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Set<Long> getSuitableSpecialDayIds() {
        return suitableSpecialDayIds;
    }

    public void setSuitableSpecialDayIds(Set<Long> suitableSpecialDayIds) {
        this.suitableSpecialDayIds = suitableSpecialDayIds;
    }

    public Set<Long> getSuitableRelationTypeIds() {
        return suitableRelationTypeIds;
    }

    public void setSuitableRelationTypeIds(Set<Long> suitableRelationTypeIds) {
        this.suitableRelationTypeIds = suitableRelationTypeIds;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }
}
