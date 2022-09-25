package pl.dgadecki.dockerandtestcontainers.business.event.api;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.shaded.org.awaitility.core.ThrowingRunnable;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.BookTicketNotification;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.command.BookTicketCommand;
import pl.dgadecki.dockerandtestcontainers.common.BaseIntegrationTest;
import pl.dgadecki.dockerandtestcontainers.common.RabbitMQConstants;
import pl.dgadecki.dockerandtestcontainers.common.utils.IntegrationTestUtils;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookTicketIntegrationTest extends BaseIntegrationTest {

    private static final String BOOK_TICKET_URL = "/tickets/bookings";

    @Test
    @SneakyThrows
    void should_send_message_to_rabbitmq_when_ticket_has_been_booked() {
        // given
        Long ticketId = 1L;
        BookTicketCommand bookTicketCommand = BookTicketCommand.builder()
                .ticketId(ticketId)
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_TICKET_URL)
                .content(IntegrationTestUtils.asJsonString(bookTicketCommand))
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        // when
        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();

        // then
        BookTicketNotification expectedBookTicketNotification = BookTicketNotification.builder()
                .eventName("Polska - Argentyna")
                .userFirstName("Damian")
                .userLastName("Gadecki")
                .build();

        ThrowingRunnable assertions = () -> {
            int numberOfMessagesInEmailNotificationQueue = getMessageCount(RabbitMQConstants.EMAIL_NOTIFICATION_QUEUE_NAME);
            int numberOfMessagesInSmsNotificationQueue = getMessageCount(RabbitMQConstants.SMS_NOTIFICATION_QUEUE_NAME);

            assertThat(numberOfMessagesInEmailNotificationQueue).isEqualTo(1L);
            assertThat(numberOfMessagesInSmsNotificationQueue).isEqualTo(1L);
        };
        Awaitility.await().atMost(5L, TimeUnit.SECONDS).untilAsserted(assertions);

        Message messageFromEmailNotificationQueue = rabbitTemplate.receive(RabbitMQConstants.EMAIL_NOTIFICATION_QUEUE_NAME);
        Message messageFromSmsNotificationQueue = rabbitTemplate.receive(RabbitMQConstants.SMS_NOTIFICATION_QUEUE_NAME);

        assertThat(messageFromEmailNotificationQueue).isNotNull();
        assertThat(messageFromSmsNotificationQueue).isNotNull();

        BookTicketNotification emailBookTicketNotification = IntegrationTestUtils.fromByteArray(messageFromEmailNotificationQueue.getBody(), BookTicketNotification.class);
        assertThat(emailBookTicketNotification).isNotNull().isEqualTo(expectedBookTicketNotification);

        BookTicketNotification smsBookTicketNotification = IntegrationTestUtils.fromByteArray(messageFromSmsNotificationQueue.getBody(), BookTicketNotification.class);
        assertThat(smsBookTicketNotification).isNotNull().isEqualTo(expectedBookTicketNotification);
    }
}
