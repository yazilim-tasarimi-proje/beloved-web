package beloved.beloved.dto;

public class AiRecommendationRequestDto {

    private Long relationTypeId;
    private Long specialDayId;

    public AiRecommendationRequestDto() {}

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
}
