package beloved.beloved.service.impl.observer;

import beloved.beloved.entity.Order;

public interface OrderObserver {
    void update(Order order, String message);
}
