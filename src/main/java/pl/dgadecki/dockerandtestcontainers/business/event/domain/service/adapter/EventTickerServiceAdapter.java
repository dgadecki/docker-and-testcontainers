package pl.dgadecki.dockerandtestcontainers.business.event.domain.service.adapter;

import org.apache.commons.lang3.StringUtils;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.model.EventTicketEntity;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.repository.EventTicketRepository;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.EventGateway;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.EventTicketService;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.BookTicketNotification;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.command.BookTicketCommand;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.query.TicketPriceQuery;
import pl.dgadecki.dockerandtestcontainers.business.user.dto.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Override
    public TicketPriceQuery findTicketPrice(Long ticketId, String discountCode) {
        BigDecimal standardPrice = BigDecimal.valueOf(2_500L);
        BigDecimal discountPrice = StringUtils.isNotBlank(discountCode) ? calculateDiscountPrice(standardPrice, discountCode) : standardPrice;

        return TicketPriceQuery.builder()
                .ticketId(ticketId)
                .standardPrice(standardPrice.setScale(2, RoundingMode.HALF_UP))
                .discountPrice(discountPrice.setScale(2, RoundingMode.HALF_UP))
                .build();
    }

    private BigDecimal calculateDiscountPrice(BigDecimal standardPrice, String discountCode) {
        Long discountInPercentage = eventGateway.getDiscountByDiscountCode(discountCode);
        if (discountInPercentage != 0) {
            return BigDecimal.valueOf(100L).subtract(BigDecimal.valueOf(discountInPercentage)).multiply(new BigDecimal("0.01")).multiply(standardPrice);
        }
        return standardPrice;
    }
}
