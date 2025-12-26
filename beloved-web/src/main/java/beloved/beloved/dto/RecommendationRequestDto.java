package beloved.beloved.dto;

import java.math.BigDecimal;
import java.util.Set;

public class RecommendationRequestDto {

    private Long relationTypeId;
    private Long specialDayId;
    private Set<Long> categoryIds;
    private Integer targetAge;
    private String favoriteColor;
    private BigDecimal maxBudget;

    public RecommendationRequestDto() {
    }

    public RecommendationRequestDto(Long relationTypeId, Long specialDayId, Set<Long> categoryIds, Integer targetAge, String favoriteColor, BigDecimal maxBudget) {
        this.relationTypeId = relationTypeId;
        this.specialDayId = specialDayId;
        this.categoryIds = categoryIds;
        this.targetAge = targetAge;
        this.favoriteColor = favoriteColor;
        this.maxBudget = maxBudget;
    }

    public Long getRelationTypeId() {
        return relationTypeId;
    }

    public void setRelationTypeId(Long relationTypeId) {
        this.relationTypeId = relationTypeId;
    }

    public Long getSpecialDayId() {
        return specialDayId;
    }

    public void setSpecialDayId(Long specialDayId) {
        this.specialDayId = specialDayId;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Integer getTargetAge() {
        return targetAge;
    }

    public void setTargetAge(Integer targetAge) {
        this.targetAge = targetAge;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public BigDecimal getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(BigDecimal maxBudget) {
        this.maxBudget = maxBudget;
    }
}
