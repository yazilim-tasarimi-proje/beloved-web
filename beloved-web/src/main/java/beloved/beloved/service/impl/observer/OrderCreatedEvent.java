package beloved.beloved.service.impl.observer;

import beloved.beloved.entity.Order;

public class OrderCreatedEvent {

    private final Order order;

    public OrderCreatedEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
