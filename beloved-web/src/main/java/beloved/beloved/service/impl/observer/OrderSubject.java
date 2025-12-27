package beloved.beloved.service.impl.observer;

import beloved.beloved.entity.Order;

public interface OrderSubject {
    void addObserver(OrderObserver observer);
    void notifyObservers(Order order, String message);
}
