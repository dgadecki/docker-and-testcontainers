package pl.dgadecki.dockerandtestcontainers.configuration.amqp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Configuration
public class RabbitMQConfiguration {

    private static final String EVENT_SERVICE_ID = "event_service";

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper jsonObjectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return new Jackson2JsonMessageConverter(jsonObjectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.addBeforePublishPostProcessors(eventServiceBeforePublishMessagePostProcessor());
        return rabbitTemplate;
    }

    private MessagePostProcessor eventServiceBeforePublishMessagePostProcessor() {
        return message -> {
            message.getMessageProperties().setAppId(EVENT_SERVICE_ID);
            message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
            message.getMessageProperties().setTimestamp(Date.from(ZonedDateTime.now(ZoneId.of("Europe/Warsaw")).toInstant()));
            message.getMessageProperties().setMessageId(UUID.randomUUID().toString());

            return message;
        };
    }
}
