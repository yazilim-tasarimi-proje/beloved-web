package beloved.beloved.dto;

//kullanıcıydan alınacak veriler
public class AddToCartRequest {
    private String email;
    private Long productId;
    private int quantity;

    public AddToCartRequest(String email, Long productId, int quantity) {
        this.email = email;
        this.productId = productId;
        this.quantity = quantity;
    }

    public AddToCartRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
