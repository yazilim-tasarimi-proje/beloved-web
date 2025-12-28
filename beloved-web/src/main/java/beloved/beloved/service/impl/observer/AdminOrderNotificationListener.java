package beloved.beloved.service.impl.observer;

import beloved.beloved.entity.Notification;
import beloved.beloved.entity.Order;
import beloved.beloved.repository.NotificationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AdminOrderNotificationListener {

    private final NotificationRepository notificationRepository;

    public AdminOrderNotificationListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        Order order = event.getOrder();

        Notification notification = new Notification();
        notification.setUser(null); // admin bildirimi
        notification.setMessage(
                "Yeni sipariş alındı. Sipariş No: " + order.getId() +
                        " | Kullanıcı: " + order.getUser().getEmail()
        );

        notificationRepository.save(notification);
    }
}

