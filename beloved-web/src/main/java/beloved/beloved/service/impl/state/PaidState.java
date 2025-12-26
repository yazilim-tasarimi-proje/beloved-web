package beloved.beloved.service.impl.state;

import beloved.beloved.entity.Order;
import beloved.beloved.entity.OrderStatus;

public class PaidState implements IOrderState {
    @Override
    public void pay(Order context) {
        throw new RuntimeException("Bu sipariş zaten ödendi!");
    }

    @Override
    public void ship(Order context) {
        System.out.println("Sipariş kargoya veriliyor...");
        context.setStatus(OrderStatus.SHIPPED);
    }

    @Override
    public void cancel(Order context) {
        System.out.println("Para iadesi yapılıyor ve sipariş iptal ediliyor...");
        // Burada iade logic'i çalışır
        context.setStatus(OrderStatus.CANCELLED);
    }
}
