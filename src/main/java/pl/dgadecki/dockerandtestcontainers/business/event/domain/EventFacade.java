package pl.dgadecki.dockerandtestcontainers.business.event.domain;

import pl.dgadecki.dockerandtestcontainers.business.event.dto.Event;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.command.BookTicketCommand;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.query.EventsQuery;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.query.TicketPriceQuery;

/**
 * Business facade for all Event related operations.
 */
public interface EventFacade {

    /**
     * Finds {@link EventsQuery} that contains list of {@link Event} whose name includes the name given as a method parameter.
     *
     * @param name name that should contain the name of the event that will be found
     * @return {@link EventsQuery} hat contains list of {@link Event} with a name containing the name given as a parameter
     */
    EventsQuery findAllEventsByName(String name);

    /**
     * Books a ticket with id given in the command object for the currently authenticated user.
     *
     * @param bookTicketCommand {@link BookTicketCommand} with id of ticket that should be booked
     */
    void bookTicket(BookTicketCommand bookTicketCommand);

    /**
     * Finds the price for a ticket with the given id and includes the discount based on the given codes
     *
     * @param ticketId id of the ticket for which price should be found
     * @param discountCode discount code on the basis of which a discount can be calculated
     * @return {@link TicketPriceQuery} with found prices for the ticket with given id
     */
    TicketPriceQuery findTicketPrice(Long ticketId, String discountCode);
}
