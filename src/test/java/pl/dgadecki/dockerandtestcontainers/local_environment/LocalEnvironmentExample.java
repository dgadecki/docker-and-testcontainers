package pl.dgadecki.dockerandtestcontainers.local_environment;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;
import pl.dgadecki.dockerandtestcontainers.common.RabbitMQConstants;

@Slf4j
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@Transactional
public class LocalEnvironmentExample {

    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:14.0");

    private static final RabbitMQContainer RABBIT_MQ_CONTAINER = new RabbitMQContainer("rabbitmq:3.8-management")
            .withExchange(RabbitMQConstants.NOTIFICATION_EXCHANGE_NAME, RabbitMQConstants.FANOUT_EXCHANGE_TYPE)
            .withQueue(RabbitMQConstants.EMAIL_NOTIFICATION_QUEUE_NAME)
            .withQueue(RabbitMQConstants.SMS_NOTIFICATION_QUEUE_NAME)
            .withBinding(RabbitMQConstants.NOTIFICATION_EXCHANGE_NAME, RabbitMQConstants.EMAIL_NOTIFICATION_QUEUE_NAME)
            .withBinding(RabbitMQConstants.NOTIFICATION_EXCHANGE_NAME, RabbitMQConstants.SMS_NOTIFICATION_QUEUE_NAME);

    private static final GenericContainer<?> DISCOUNT_SERVICE_CONTAINER = new GenericContainer(DockerImageName.parse("discount-service:0.0.1"))
            .withExposedPorts(8080)
            .waitingFor(Wait.forLogMessage(".*Started DiscountServiceForDevSessionApplication.*", 1));

    @DynamicPropertySource
    static void configureApplication(DynamicPropertyRegistry dynamicPropertyRegistry) {
        Startables.deepStart(POSTGRE_SQL_CONTAINER, RABBIT_MQ_CONTAINER, DISCOUNT_SERVICE_CONTAINER).join();

        // PostgreSQL
        dynamicPropertyRegistry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);

        // RabbitMQ
        dynamicPropertyRegistry.add("spring.rabbitmq.host", RABBIT_MQ_CONTAINER::getHost);
        dynamicPropertyRegistry.add("spring.rabbitmq.port", RABBIT_MQ_CONTAINER::getAmqpPort);
        dynamicPropertyRegistry.add("spring.rabbitmq.username", RABBIT_MQ_CONTAINER::getAdminUsername);
        dynamicPropertyRegistry.add("spring.rabbitmq.password", RABBIT_MQ_CONTAINER::getAdminPassword);

        // Discount service
        dynamicPropertyRegistry.add("rest-client.discount.url", () -> "http://" + DISCOUNT_SERVICE_CONTAINER.getHost() + ":" + DISCOUNT_SERVICE_CONTAINER.getFirstMappedPort());
    }

    @Test
    @SneakyThrows
    void local_environment_in_tests() {
        log.info("---> LOCAL ENVIRONMENT WITH TEST CONTAINERS IS READY TO USE <---");
        System.in.read();
    }
}
