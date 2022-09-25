package pl.dgadecki.dockerandtestcontainers.business.event.dto;

import lombok.Builder;

@Builder
public record BookTicketNotification(
        String eventName,
        String userFirstName,
        String userLastName
) { }
