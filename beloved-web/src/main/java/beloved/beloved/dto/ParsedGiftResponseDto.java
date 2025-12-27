package beloved.beloved.dto;

public class ParsedGiftResponseDto {

    private String relationType;
    private String specialDay;
    private String color;
    private Integer maxBudget;
    private Boolean personalized;

    public String getRelationType() { return relationType; }
    public void setRelationType(String relationType) { this.relationType = relationType; }

    public String getSpecialDay() { return specialDay; }
    public void setSpecialDay(String specialDay) { this.specialDay = specialDay; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Integer getMaxBudget() { return maxBudget; }
    public void setMaxBudget(Integer maxBudget) { this.maxBudget = maxBudget; }

    public Boolean getPersonalized() { return personalized; }
    public void setPersonalized(Boolean personalized) { this.personalized = personalized; }
}
