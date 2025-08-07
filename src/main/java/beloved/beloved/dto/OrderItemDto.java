package beloved.beloved.dto;

import java.math.BigDecimal;

public class OrderItemDto {

    private Long productId;
    private String productName;
    private int quantity;

    public OrderItemDto() {
    }

    public OrderItemDto(Long productId, String productName, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    // Getters & Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
