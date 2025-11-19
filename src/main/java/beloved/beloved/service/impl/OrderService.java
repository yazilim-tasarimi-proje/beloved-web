package beloved.beloved.service.impl;

import beloved.beloved.dto.OrderDto;
import beloved.beloved.dto.OrderItemDto;
import beloved.beloved.entity.*;
import beloved.beloved.repository.CartRepository;
import beloved.beloved.repository.OrderRepository;
import beloved.beloved.repository.UserRepository;
import beloved.beloved.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional //Yapılan DB işlemlerinde eksik ve hatalı işlem varsa o veri geri döndürülür. Veritabanında hatalı
    // veri kalmaz.
    public OrderDto placeOrder(String email) {
        User user = findUserByEmail(email);
        Cart cart = findCartByUser(user);

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = createOrderFromCart(user, cart);
        orderRepository.save(order);

        clearCart(cart);

        return entityToDto(order);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Cart findCartByUser(User user) {
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    private Order createOrderFromCart(User user, Cart cart) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        Set<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProduct(cartItem.getProduct());
                    item.setQuantity(cartItem.getQuantity());
                    return item;
                })
                .collect(Collectors.toSet());

        BigDecimal totalPrice = calculateTotalPrice(cart);

        order.setPrice(totalPrice);
        order.setOrderItemSet(orderItems);
        return order;
    }

    private BigDecimal calculateTotalPrice(Cart cart) {
        return cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void clearCart(Cart cart) {
        // Sepeti temizle
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }


    @Override
    public void cancelOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderDto> getUserOrders(String email) {
        User user = findUserByEmail(email);
        return orderRepository.findByUser(user).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    private OrderDto entityToDto(Order order) {
        Set<OrderItemDto> itemDtos = order.getOrderItemSet().stream()
                .map(item -> new OrderItemDto(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity()
                ))
                .collect(Collectors.toSet());

        OrderDto dto = new OrderDto();
        dto.setOrderId(order.getId());
        dto.setEmail(order.getUser().getEmail());
        dto.setTotalPrice(order.getPrice());
        dto.setOrderDate(order.getOrderDate());
        dto.setItems(itemDtos);
        return dto;
    }
}