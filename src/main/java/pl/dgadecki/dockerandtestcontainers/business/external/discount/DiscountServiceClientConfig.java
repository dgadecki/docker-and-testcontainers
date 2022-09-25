package pl.dgadecki.dockerandtestcontainers.business.external.discount;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DiscountServiceClientConfig {

    @Value("${rest-client.discount.url}")
    private String url;

    @Bean
    public DiscountServiceClient discountServiceClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .build();

        return new DiscountServiceClientAdapter(webClient);
    }
}
