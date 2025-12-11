package beloved.beloved.service.impl;

import beloved.beloved.dto.OrderDto;
import beloved.beloved.entity.*;
import beloved.beloved.repository.*;
import beloved.beloved.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    @Transactional
    public OrderDto placeOrder(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = OrderFactory.createOrderFromCart(user, cart);
        order.setStatus(OrderStatus.CREATED);

        orderRepository.save(order);

        // Sepeti temizleme
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return OrderFactory.entityToDto(order);
    }

    @Override
    @Transactional
    public void payOrder(Long orderId) {
        Order order = findOrderById(orderId);

        order.pay();

        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void shipOrder(Long orderId) {
        Order order = findOrderById(orderId);

        //Eğer sipariş ödenmediyse CreatedState hata fırlatır.
        order.ship();

        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = findOrderById(orderId);

        //Eğer kargodaysa ShippedState hata fırlatır.
        order.cancel();

        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getUserOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user).stream()
                .map(OrderFactory::entityToDto)
                .collect(Collectors.toList());
    }
}
