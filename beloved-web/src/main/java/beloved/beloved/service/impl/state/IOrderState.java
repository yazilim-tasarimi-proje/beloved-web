package beloved.beloved.service.impl.state;

import beloved.beloved.entity.Order;

public interface IOrderState {
    void pay(Order context);
    void ship(Order context);
    void cancel(Order context);

}