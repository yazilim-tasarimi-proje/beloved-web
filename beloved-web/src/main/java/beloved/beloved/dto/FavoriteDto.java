package beloved.beloved.dto;

public class FavoriteDto {

    private Long id;
    private String userEmail;
    private Long productId;

    public FavoriteDto(Long id, String userEmail, Long productId){
        this.id=id;
        this.userEmail=userEmail;
        this.productId=productId;
    }

    public FavoriteDto(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }}
