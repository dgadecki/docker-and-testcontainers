package pl.dgadecki.dockerandtestcontainers.business.event.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.EventFacade;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.command.BookTicketCommand;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class EventTicketRestController {

    private final EventFacade eventFacade;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "/bookings", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void bookTicket(@RequestBody BookTicketCommand bookTicketCommand) {
        eventFacade.bookTicket(bookTicketCommand);
    }
}
