package beloved.beloved.service.impl.factory;

import beloved.beloved.dto.OrderDto;
import beloved.beloved.dto.OrderItemDto;
import beloved.beloved.entity.Cart;
import beloved.beloved.entity.Order;
import beloved.beloved.entity.OrderItem;
import beloved.beloved.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


 // Basit factory sınıfı: Cart ve User verilerinden Order nesnesi oluşturur ve
// Order entity'sini DTO'ya dönüştürür.

public class OrderFactory {

    public static Order createOrderFromCart(User user, Cart cart) {
        if (user == null || cart == null) return null;

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

