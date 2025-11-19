package beloved.beloved.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class OrderDto {

    private Long orderId;
    private String email;
    private BigDecimal totalPrice;
    private LocalDateTime orderDate;
    private Set<OrderItemDto> items;

    public OrderDto() {
    }

    public OrderDto(Long orderId, String email, BigDecimal totalPrice, LocalDateTime orderDate, Set<OrderItemDto> items) {
        this.orderId = orderId;
        this.email = email;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.items = items;
    }

    // Getters & Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Set<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(Set<OrderItemDto> items) {
        this.items = items;
    }
}
