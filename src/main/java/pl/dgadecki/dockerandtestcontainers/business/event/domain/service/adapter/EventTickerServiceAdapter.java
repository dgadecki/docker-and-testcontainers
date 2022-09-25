package pl.dgadecki.dockerandtestcontainers.business.event.domain.service.adapter;

import pl.dgadecki.dockerandtestcontainers.business.event.domain.model.EventTicketEntity;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.repository.EventTicketRepository;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.EventGateway;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.EventTicketService;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.BookTicketNotification;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.command.BookTicketCommand;
import pl.dgadecki.dockerandtestcontainers.business.user.dto.User;

import java.time.LocalDateTime;

public record EventTickerServiceAdapter(
        EventGateway eventGateway,
        EventTicketRepository eventTicketRepository
) implements EventTicketService {

    @Override
    public void bookTicket(BookTicketCommand bookTicketCommand) {
        User user = eventGateway.getAuthenticatedUser();
        EventTicketEntity eventTicket = eventTicketRepository.findById(bookTicketCommand.ticketId()).orElseThrow();
        if (eventTicket.getUserId() == null) {
            bookTicketForUser(eventTicket, user);
            sendBookTicketNotification(eventTicket, user);
        }
    }

    private void bookTicketForUser(EventTicketEntity eventTicketEntity, User user) {
        eventTicketEntity.setUserId(user.uuid());
        eventTicketEntity.setBookingDateTime(LocalDateTime.now());
    }

    private void sendBookTicketNotification(EventTicketEntity eventTicketEntity, User user) {
        BookTicketNotification bookTicketNotification = BookTicketNotification.builder()
                .eventName(eventTicketEntity.getEventEntity().getName())
                .userFirstName(user.firstName())
                .userLastName(user.lastName())
                .build();

        eventGateway.publishBookTicketNotification(bookTicketNotification);
    }
}
