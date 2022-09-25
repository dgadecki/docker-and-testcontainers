package pl.dgadecki.dockerandtestcontainers.business.event.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.EventFacade;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.command.BookTicketCommand;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.query.TicketPriceQuery;

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

    @GetMapping("/{id}/prices")
    public TicketPriceQuery findTicketPrice(@PathVariable("id") Long ticketId, @RequestParam(required = false, value = "discountCode") String discountCode) {
        return eventFacade.findTicketPrice(ticketId, discountCode);
    }
}
