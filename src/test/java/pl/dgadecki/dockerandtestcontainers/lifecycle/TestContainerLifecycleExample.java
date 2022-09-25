package pl.dgadecki.dockerandtestcontainers.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Testcontainers
public class TestContainerLifecycleExample {

    @Container
    private static final PostgreSQLContainer<?> sharedPostgreSQLContainer = new PostgreSQLContainer<>("postgres:14.0");

    @Container
    private final RabbitMQContainer restartedRabbitMQContainer = new RabbitMQContainer("rabbitmq:3.8-management");

    @Test
    void test_1() {
        log.info("------ PostgreSQL container port: {}", sharedPostgreSQLContainer.getFirstMappedPort());
        log.info("------ RabbitMQ container port: {}", restartedRabbitMQContainer.getFirstMappedPort());
        assertThat(sharedPostgreSQLContainer.isRunning()).isTrue();
        assertThat(restartedRabbitMQContainer.isRunning()).isTrue();
    }

    @Test
    void test_2() {
        log.info("------ PostgreSQL container port: {}", sharedPostgreSQLContainer.getFirstMappedPort());
        log.info("------ RabbitMQ container port: {}", restartedRabbitMQContainer.getFirstMappedPort());
        assertThat(sharedPostgreSQLContainer.isRunning()).isTrue();
        assertThat(restartedRabbitMQContainer.isRunning()).isTrue();
    }

    @Test
    void test_3() {
        log.info("------ PostgreSQL container port: {}", sharedPostgreSQLContainer.getFirstMappedPort());
        log.info("------ RabbitMQ container port: {}", restartedRabbitMQContainer.getFirstMappedPort());
        assertThat(sharedPostgreSQLContainer.isRunning()).isTrue();
        assertThat(restartedRabbitMQContainer.isRunning()).isTrue();
    }

    @Test
    void test_4() {
        log.info("------ PostgreSQL container port: {}", sharedPostgreSQLContainer.getFirstMappedPort());
        log.info("------ RabbitMQ container port: {}", restartedRabbitMQContainer.getFirstMappedPort());
        assertThat(sharedPostgreSQLContainer.isRunning()).isTrue();
        assertThat(restartedRabbitMQContainer.isRunning()).isTrue();
    }
}
