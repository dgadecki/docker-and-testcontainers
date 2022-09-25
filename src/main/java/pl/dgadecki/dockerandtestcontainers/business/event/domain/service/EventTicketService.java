package pl.dgadecki.dockerandtestcontainers.business.event.domain.service;

import pl.dgadecki.dockerandtestcontainers.business.event.dto.command.BookTicketCommand;

public interface EventTicketService {

    /**
     * Books a ticket with id given in the command object for the currently authenticated user.
     *
     * @param bookTicketCommand {@link BookTicketCommand} with id of ticket that should be booked
     */
    void bookTicket(BookTicketCommand bookTicketCommand);
}
