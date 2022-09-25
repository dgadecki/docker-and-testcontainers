package pl.dgadecki.dockerandtestcontainers.business.event.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.dgadecki.dockerandtestcontainers.business.event.api.EventMessagePublisher;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.repository.EventRepository;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.repository.EventTicketRepository;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.EventGateway;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.EventService;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.EventTicketService;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.adapter.EventGatewayAdapter;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.adapter.EventServiceAdapter;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.service.adapter.EventTickerServiceAdapter;
import pl.dgadecki.dockerandtestcontainers.business.external.discount.DiscountServiceClient;
import pl.dgadecki.dockerandtestcontainers.business.user.domain.UserFacade;

@Configuration
public class EventConfig {

    @Bean
    public EventFacade eventFacade(UserFacade userFacade,
                                   DiscountServiceClient discountServiceClient,
                                   EventMessagePublisher eventMessagePublisher,
                                   EventRepository eventRepository,
                                   EventTicketRepository eventTicketRepository) {
        // Services
        EventGateway eventGateway = new EventGatewayAdapter(
                userFacade,
                discountServiceClient,
                eventMessagePublisher
        );
        EventService eventService = new EventServiceAdapter(
                eventRepository
        );
        EventTicketService eventTicketService = new EventTickerServiceAdapter(
                eventGateway,
                eventTicketRepository
        );

        return new EventFacadeAdapter(
                eventService,
                eventTicketService
        );
    }
}
