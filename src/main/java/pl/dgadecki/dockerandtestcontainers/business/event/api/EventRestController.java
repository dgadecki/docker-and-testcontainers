package pl.dgadecki.dockerandtestcontainers.business.event.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.EventFacade;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.query.EventsQuery;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventRestController {

    private final EventFacade eventFacade;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public EventsQuery findAllEventsByName(@RequestParam("name") String name) {
        return eventFacade.findAllEventsByName(name);
    }
}
