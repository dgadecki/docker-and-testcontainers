package pl.dgadecki.dockerandtestcontainers.business.event.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.EventService;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.EventTicketService;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.command.BookTicketCommand;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.query.EventsQuery;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.query.TicketPriceQuery;

@Transactional
@RequiredArgsConstructor
public class EventFacadeAdapter implements EventFacade {

    private final EventService eventService;
    private final EventTicketService eventTicketService;

    @Override
    public EventsQuery findAllEventsByName(String name) {
        return eventService.findAllEventsByName(name);
    }

    @Override
    public void bookTicket(BookTicketCommand bookTicketCommand) {
        eventTicketService.bookTicket(bookTicketCommand);
    }

    @Override
    public TicketPriceQuery findTicketPrice(Long ticketId, String discountCode) {
        return eventTicketService.findTicketPrice(ticketId, discountCode);
    }
}
