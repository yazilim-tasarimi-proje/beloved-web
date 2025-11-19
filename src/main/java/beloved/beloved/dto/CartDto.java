package beloved.beloved.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

//clienta gönderilecek verilerin bulunduğu dto
public class CartDto {
    private LocalDateTime createdAt;
    private Set<CartItemDto> cartItems;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private BigDecimal discountPrice;
    public CartDto() {
    }

    public CartDto(LocalDateTime createdAt, Set<CartItemDto> cartItems, BigDecimal totalPrice, BigDecimal discount, BigDecimal discountPrice) {
        this.createdAt = createdAt;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.discountPrice = discountPrice;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }
}
