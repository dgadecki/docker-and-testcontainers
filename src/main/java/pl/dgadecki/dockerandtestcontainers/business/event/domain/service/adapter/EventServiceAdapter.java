package pl.dgadecki.dockerandtestcontainers.business.event.domain.service.adapter;

import pl.dgadecki.dockerandtestcontainers.business.event.domain.repository.EventRepository;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.EventService;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.Event;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.query.EventsQuery;

import java.util.List;

public record EventServiceAdapter(
        EventRepository eventRepository
) implements EventService {

    @Override
    public EventsQuery findAllEventsByName(String name) {
        List<Event> events = eventRepository.findAllEventsByName(name);
        return EventsQuery.builder()
                .events(events)
                .build();
    }
}
