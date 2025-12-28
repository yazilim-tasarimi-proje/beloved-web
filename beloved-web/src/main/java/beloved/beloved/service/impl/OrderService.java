package beloved.beloved.service.impl;

import beloved.beloved.dto.OrderDto;
import beloved.beloved.entity.*;
import beloved.beloved.repository.CartRepository;
import beloved.beloved.repository.OrderRepository;
import beloved.beloved.repository.UserRepository;
import beloved.beloved.service.IOrderService;
import beloved.beloved.service.impl.factory.OrderFactory;
import beloved.beloved.service.impl.observer.OrderCreatedEvent;
import beloved.beloved.service.impl.stateOrder.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        CartRepository cartRepository,
                        ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public OrderDto placeOrder(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Factory
        Order order = OrderFactory.createOrderFromCart(user, cart);

        //  CREATED
        order.setStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);

        // Observer tetiklenir
        eventPublisher.publishEvent(new OrderCreatedEvent(savedOrder));

        // Sepeti temizle
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return OrderFactory.entityToDto(savedOrder);
    }

    @Override
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        IOrderState state = resolveState(order.getStatus());

        state.cancel(order);

        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getUserOrders(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user)
                .stream()
                .map(OrderFactory::entityToDto)
                .collect(Collectors.toList());
    }

    private IOrderState resolveState(OrderStatus status) {
        return switch (status) {
            case CREATED -> new CreatedState();
            case PAID -> new PaidState();
            case SHIPPED -> new ShippedState();
            case CANCELLED -> new CancelledState();
        };
    }
}
