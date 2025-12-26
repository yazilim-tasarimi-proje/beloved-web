package beloved.beloved.service.impl.state;

import beloved.beloved.entity.Order;

public class CancelledState implements IOrderState {
    @Override
    public void pay(Order context) {
        throw new RuntimeException("İptal edilmiş sipariş ödenemez. Yeni sipariş oluşturun.");
    }
    @Override
    public void ship(Order context) {
        throw new RuntimeException("İptal edilmiş sipariş kargolanamaz.");
    }

    @Override
    public void cancel(Order context) {
        throw new RuntimeException("Sipariş zaten iptal edilmiş.");
    }
}
