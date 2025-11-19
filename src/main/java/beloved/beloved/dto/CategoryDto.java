package beloved.beloved.dto;

public class CategoryDto {
    private String name;
    private Long id;
    private Long parentId;

    public CategoryDto(String name  , Long id, Long parentId) {
        this.name = name;
        this.id = id;
        this.parentId = parentId;
    }

    public CategoryDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
