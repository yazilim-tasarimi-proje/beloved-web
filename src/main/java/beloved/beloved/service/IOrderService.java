package beloved.beloved.service;

import beloved.beloved.dto.OrderDto;

import java.util.List;

public interface IOrderService {
    OrderDto placeOrder(String email);
    void payOrder(Long orderId);
    void shipOrder(Long orderId);
    void cancelOrder(Long orderId);
    List<OrderDto> getUserOrders(String email);

}
