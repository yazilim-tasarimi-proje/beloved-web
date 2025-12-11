package beloved.beloved.entity;

import beloved.beloved.service.IOrderState;
import beloved.beloved.service.impl.CancelledState;
import beloved.beloved.service.impl.CreatedState;
import beloved.beloved.service.impl.PaidState;
import beloved.beloved.service.impl.ShippedState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItemSet= new HashSet<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    //veritabanındaki Enum'a bakıp canlı State nesnesini üretilir.
    //Spring Bean olmadıkları için 'new' ile üretilir.
    public IOrderState getOrderState() {
        switch (this.status) {
            case CREATED: return new CreatedState();
            case PAID:    return new PaidState();
            case SHIPPED: return new ShippedState();
            case CANCELLED: return new CancelledState();
            default: return new CreatedState();
        }
    }

    public Order(Long id, BigDecimal price, LocalDateTime orderDate, User user, Set<OrderItem> orderItemSet) {
        this.id = id;
        this.price = price;
        this.orderDate = orderDate;
        this.user = user;
        this.orderItemSet = orderItemSet;
    }
    public Order() {
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
    public void pay() {
        getOrderState().pay(this);
    }

    public void ship() {
        getOrderState().ship(this);
    }

    public void cancel() {
        getOrderState().cancel(this);
    }


    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

