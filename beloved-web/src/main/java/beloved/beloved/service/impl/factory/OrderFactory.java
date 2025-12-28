package beloved.beloved.service.impl.factory;

import beloved.beloved.dto.OrderDto;
import beloved.beloved.dto.OrderItemDto;
import beloved.beloved.entity.Cart;
import beloved.beloved.entity.Order;
import beloved.beloved.entity.OrderItem;
import beloved.beloved.entity.OrderStatus;
import beloved.beloved.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderFactory {

    public static Order createOrderFromCart(User user, Cart cart) {
        if (user == null || cart == null) return null;

        // 1️⃣ OrderItem'ları oluştur
        Set<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem item = new OrderItem();
                    item.setProduct(cartItem.getProduct());
                    item.setQuantity(cartItem.getQuantity());
                    return item;
                })
                .collect(Collectors.toSet());

        // 2️⃣ Toplam fiyat hesapla
        BigDecimal totalPrice = calculateTotalPrice(cart);

        // 3️⃣ Order'ı Builder ile oluştur
        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .price(totalPrice)
                .status(OrderStatus.CREATED)
                .orderItems(orderItems)
                .build();

        // 4️⃣ OrderItem → Order ilişkisini bağla
        orderItems.forEach(item -> item.setOrder(order));

        return order;
    }

    public static BigDecimal calculateTotalPrice(Cart cart) {
        if (cart == null) return BigDecimal.ZERO;

        return cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static OrderDto entityToDto(Order order) {
        if (order == null) return null;

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
