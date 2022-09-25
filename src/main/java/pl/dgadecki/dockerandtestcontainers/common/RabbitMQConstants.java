package pl.dgadecki.dockerandtestcontainers.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RabbitMQConstants {

    /** Configuration */
    public static String FANOUT_EXCHANGE_TYPE = "fanout";

    /** Exchanges */
    public static final String NOTIFICATION_EXCHANGE_NAME = "notification";

    /** Queues */
    public static final String EMAIL_NOTIFICATION_QUEUE_NAME = "notification.email";
    public static final String SMS_NOTIFICATION_QUEUE_NAME = "notification.sms";
}
