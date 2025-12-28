package beloved.beloved.controller;

import beloved.beloved.controller.Facade.OrderFacade;
import beloved.beloved.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderFacade orderFacade;

    public OrderController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @PostMapping("/place")
    public ResponseEntity<OrderDto> placeOrder(@RequestParam String email) {
        return ResponseEntity.ok(orderFacade.placeOrder(email));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderFacade.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled successfully");
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDto>> getUserOrders(@RequestParam String email) {
        return ResponseEntity.ok(orderFacade.getUserOrders(email));
    }
}
