package pl.dgadecki.dockerandtestcontainers.business.event.dto.command;

import lombok.Builder;

@Builder
public record BookTicketCommand(
        Long ticketId
) { }
