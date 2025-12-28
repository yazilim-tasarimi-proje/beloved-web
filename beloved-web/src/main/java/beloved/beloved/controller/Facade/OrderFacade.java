package beloved.beloved.controller.Facade;


import beloved.beloved.dto.OrderDto;
import beloved.beloved.service.IOrderService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderFacade {

    private final IOrderService orderService;

    public OrderFacade(IOrderService orderService) {
        this.orderService = orderService;
    }

    public OrderDto placeOrder(String email) {
        return orderService.placeOrder(email);
    }

    public void cancelOrder(Long orderId) {
        orderService.cancelOrder(orderId);
    }

    public List<OrderDto> getUserOrders(String email) {
        return orderService.getUserOrders(email);
    }
}
