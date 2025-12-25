package beloved.beloved.service.impl;

import beloved.beloved.entity.Order;
import beloved.beloved.entity.OrderStatus;
import beloved.beloved.service.IOrderState;

public class CreatedState implements IOrderState {
    @Override
    public void pay(Order context) {
        System.out.println("Ödeme alınıyor...");
        context.setStatus(OrderStatus.PAID);
    }

    @Override
    public void ship(Order context) {
        throw new RuntimeException("Ödenmemiş sipariş kargolanamaz!");
    }

    @Override
    public void cancel(Order context) {
        System.out.println("Sipariş iptal ediliyor...");
        context.setStatus(OrderStatus.CANCELLED);
    }
}

