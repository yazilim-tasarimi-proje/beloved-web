package beloved.beloved.controller;

import beloved.beloved.dto.OrderDto;
import beloved.beloved.service.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    // 1. Sipariş Oluştur
    @PostMapping("/place")
    public ResponseEntity<OrderDto> placeOrder(@RequestParam String email) {
        OrderDto order = orderService.placeOrder(email);
        return ResponseEntity.ok(order);
    }

    // 2. Sipariş İptal Et
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled successfully");
    }

    // 3. Kullanıcının Siparişlerini Listele
    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(@RequestParam String email) {
        List<OrderDto> orders = orderService.getUserOrders(email);
        return ResponseEntity.ok(orders);
    }
}
