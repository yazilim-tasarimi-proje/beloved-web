package beloved.beloved.service.impl;

import beloved.beloved.entity.Order;
import beloved.beloved.service.IOrderState;

public class ShippedState implements IOrderState {
    @Override
    public void pay(Order context) {
        throw new RuntimeException("Ödenmiş ve kargolanmış sipariş tekrar ödenemez.");
    }

    @Override
    public void ship(Order context) {
        throw new RuntimeException("Sipariş zaten kargoda.");
    }

    @Override
    public void cancel(Order context) {
        throw new RuntimeException("Kargoya verilmiş sipariş iptal edilemez! İade sürecini başlatın.");
    }
}
