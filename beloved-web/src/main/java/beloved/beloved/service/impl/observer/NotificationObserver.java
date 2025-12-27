package beloved.beloved.service.impl.observer;


import beloved.beloved.entity.Notification;
import beloved.beloved.entity.Order;
import beloved.beloved.repository.NotificationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationObserver implements OrderObserver {

    private final NotificationRepository notificationRepository;

    public NotificationObserver(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void update(Order order, String message) {
        Notification notification = new Notification();
        notification.setUser(order.getUser());
        notification.setMessage(message);

        notificationRepository.save(notification);
    }
}
