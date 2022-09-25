package pl.dgadecki.dockerandtestcontainers.business.external.discount;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import pl.dgadecki.dockerandtestcontainers.business.external.discount.dto.ExternalDiscount;

@RequiredArgsConstructor
public class DiscountServiceClientAdapter implements DiscountServiceClient {

    private final WebClient webClient;

    @Override
    public Long getDiscountByDiscountCode(String discountCode) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/discounts").queryParam("discountCode", discountCode).build())
                .retrieve()
                .bodyToMono(ExternalDiscount.class)
                .block()
                .discountValueInPercentage();
    }
}
