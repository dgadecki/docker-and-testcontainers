package pl.dgadecki.dockerandtestcontainers.business.event.api;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.BookTicketNotification;
import pl.dgadecki.dockerandtestcontainers.common.RabbitMQConstants;

@Component
@RequiredArgsConstructor
public class EventMessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishBookTicketNotification(BookTicketNotification bookTicketNotification) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.NOTIFICATION_EXCHANGE_NAME, "", bookTicketNotification);
    }
}
