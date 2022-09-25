package pl.dgadecki.dockerandtestcontainers.business.event.domain;

import pl.dgadecki.dockerandtestcontainers.business.event.dto.Event;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.command.BookTicketCommand;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.query.EventsQuery;

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
}
