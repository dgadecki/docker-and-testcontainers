package pl.dgadecki.dockerandtestcontainers.business.event.domain.service;

import pl.dgadecki.dockerandtestcontainers.business.event.dto.command.BookTicketCommand;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.query.TicketPriceQuery;

public interface EventTicketService {

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
