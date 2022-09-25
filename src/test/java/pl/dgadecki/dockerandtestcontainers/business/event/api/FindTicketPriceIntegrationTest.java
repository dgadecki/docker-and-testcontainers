package pl.dgadecki.dockerandtestcontainers.business.event.api;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.query.TicketPriceQuery;
import pl.dgadecki.dockerandtestcontainers.common.BaseIntegrationTest;
import pl.dgadecki.dockerandtestcontainers.common.utils.IntegrationTestUtils;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FindTicketPriceIntegrationTest extends BaseIntegrationTest {

    private static final String FIND_TICKET_PRICE_URL = "/tickets/{id}/prices";
    private static final String DISCOUNT_CODE_REQUEST_PARAM_NAME = "discountCode";

    @Test
    @SneakyThrows
    void should_return_ticket_prices_when_discount_code_has_not_been_given() {
        // given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(FIND_TICKET_PRICE_URL, 1L)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getResponse()).isNotNull();

        TicketPriceQuery result = IntegrationTestUtils.fromMvcResult(response, TicketPriceQuery.class);
        assertThat(result).isNotNull();
        assertThat(result.standardPrice()).isEqualTo(new BigDecimal("2500.00"));
        assertThat(result.discountPrice()).isEqualTo(new BigDecimal("2500.00"));
    }

    @Test
    @SneakyThrows
    void should_return_ticket_prices_when_discount_code_offers_50_percent_discount() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(FIND_TICKET_PRICE_URL, 1L)
                .param(DISCOUNT_CODE_REQUEST_PARAM_NAME, "docker")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getResponse()).isNotNull();

        TicketPriceQuery result = IntegrationTestUtils.fromMvcResult(response, TicketPriceQuery.class);
        assertThat(result).isNotNull();
        assertThat(result.standardPrice()).isEqualTo(new BigDecimal("2500.00"));
        assertThat(result.discountPrice()).isEqualTo(new BigDecimal("1250.00"));
    }

    @Test
    @SneakyThrows
    void should_return_ticket_prices_when_discount_code_offers_75_percent_discount() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(FIND_TICKET_PRICE_URL, 1L)
                .param(DISCOUNT_CODE_REQUEST_PARAM_NAME, "docker_and_testcontainers")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getResponse()).isNotNull();

        TicketPriceQuery result = IntegrationTestUtils.fromMvcResult(response, TicketPriceQuery.class);
        assertThat(result).isNotNull();
        assertThat(result.standardPrice()).isEqualTo(new BigDecimal("2500.00"));
        assertThat(result.discountPrice()).isEqualTo(new BigDecimal("625.00"));
    }
}
