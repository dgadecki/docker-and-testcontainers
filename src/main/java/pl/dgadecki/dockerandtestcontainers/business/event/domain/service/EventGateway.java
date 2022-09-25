package pl.dgadecki.dockerandtestcontainers.business.event.domain.service;

import pl.dgadecki.dockerandtestcontainers.business.event.dto.BookTicketNotification;
import pl.dgadecki.dockerandtestcontainers.business.user.dto.User;

/**
 * Technical service responsible for communication Event module with external modules and services.
 */
public interface EventGateway {

    /**
     * Returns currently authenticated user.
     *
     * @return {@link User} with the data of the currently authenticated user
     */
    User getAuthenticatedUser();

    /**
     * Publish a message to RabbitMQ to notify the user that the ticket has been booked.
     *
     * @param bookTicketNotification {@link BookTicketNotification} with data for notification.
     */
    void publishBookTicketNotification(BookTicketNotification bookTicketNotification);

    /**
     * Returns the discount value as a percentage for the provided discount code.
     *
     * @param discountCode the discount code for which the discount is to be found
     * @return value of the discount in percentage
     */
    Long getDiscountByDiscountCode(String discountCode);
}
