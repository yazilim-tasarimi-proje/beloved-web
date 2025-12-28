package beloved.beloved.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<OrderItem> orderItemSet = new HashSet<>();

    protected Order() {

    }

    private Order(Builder builder) {
        this.id = builder.id;
        this.price = builder.price;
        this.orderDate = builder.orderDate;
        this.user = builder.user;
        this.status = builder.status;
        this.orderItemSet = builder.orderItemSet != null
                ? builder.orderItemSet
                : new HashSet<>();
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;
        private BigDecimal price;
        private LocalDateTime orderDate;
        private User user;
        private OrderStatus status;
        private Set<OrderItem> orderItemSet = new HashSet<>();

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder orderItems(Set<OrderItem> orderItemSet) {
            this.orderItemSet = orderItemSet;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderItem> getOrderItemSet() {
        return orderItemSet;
    }

    public void setOrderItemSet(Set<OrderItem> orderItemSet) {
        this.orderItemSet = orderItemSet;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
