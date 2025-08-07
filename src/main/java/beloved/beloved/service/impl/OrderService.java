package beloved.beloved.service.impl;

import beloved.beloved.dto.OrderDto;
import beloved.beloved.dto.OrderItemDto;
import beloved.beloved.entity.*;
import beloved.beloved.repository.*;
import beloved.beloved.service.IOrderService;
import org.springframework.stereotype.Service;

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

    // 1. Sipariş Oluştur
    @Override
    public OrderDto placeOrder(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        Set<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProduct(cartItem.getProduct());
                    item.setQuantity(cartItem.getQuantity());  // quantity int kalabilir
                    return item;
                })
                .collect(Collectors.toSet());

        // Toplam fiyatı BigDecimal olarak hesapla
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setPrice(totalPrice); // price BigDecimal olmalı
        order.setOrderItemSet(orderItems);

        orderRepository.save(order);

        // Sepeti temizle
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return entityToDto(order);
    }

    // 2. Sipariş İptal Et
    @Override
    public void cancelOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }

    // 3. Kullanıcının Siparişlerini Listele
    @Override
    public List<OrderDto> getUserOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    // Dönüştürücü metot
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
        dto.setTotalPrice(order.getPrice()); // BigDecimal tipiyle uyumlu olmalı
        dto.setOrderDate(order.getOrderDate());
        dto.setItems(itemDtos);
        return dto;
    }
}
