package beloved.beloved.service.impl.observer;

import beloved.beloved.entity.Notification;
import beloved.beloved.entity.Order;
import beloved.beloved.repository.NotificationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserOrderNotificationListener {

    private final NotificationRepository notificationRepository;

    public UserOrderNotificationListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        Order order = event.getOrder();

        Notification notification = new Notification();
        notification.setUser(order.getUser());
        notification.setMessage(
                "Siparişiniz başarıyla oluşturuldu. Sipariş No: " + order.getId()
        );

        notificationRepository.save(notification);
    }
}
