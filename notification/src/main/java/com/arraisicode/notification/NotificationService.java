package com.arraisicode.notification;

import com.arraisicode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void save(NotificationRequest request) {
        notificationRepository.save(Notification.builder()
                .message(request.message())
                .sender("Arraisicode")
                .toCustomerEmail(request.toCustomerEmail())
                .toCustomerId(request.toCustomerId())
                .sendAt(LocalDateTime.now())
                .build());
    }
}
