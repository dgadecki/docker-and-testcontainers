package pl.dgadecki.dockerandtestcontainers.business.event.api;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.Event;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.query.EventsQuery;
import pl.dgadecki.dockerandtestcontainers.common.BaseIntegrationTest;
import pl.dgadecki.dockerandtestcontainers.common.utils.IntegrationTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FindAllEventsByNameIntegrationTests extends BaseIntegrationTest {

    private static final String FIND_ALL_EVENTS_BY_NAME_URL = "/events";
    private static final String NAME_REQUEST_PARAM = "name";

    @Test
    @SneakyThrows
    void should_return_result_with_empty_list_when_no_event_exists_with_given_name() {
        // given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(FIND_ALL_EVENTS_BY_NAME_URL)
                .param(NAME_REQUEST_PARAM, "Samoa")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        // when
        MvcResult response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getResponse()).isNotNull();

        EventsQuery result = IntegrationTestUtils.fromMvcResult(response, EventsQuery.class);
        assertThat(result).isNotNull();
        assertThat(result.events()).isEmpty();
    }

    @Test
    @SneakyThrows
    void should_return_result_with_events_that_contain_given_name_in_event_name() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(FIND_ALL_EVENTS_BY_NAME_URL)
                .param(NAME_REQUEST_PARAM, "Polska")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        // when
        MvcResult response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getResponse()).isNotNull();

        EventsQuery result = IntegrationTestUtils.fromMvcResult(response, EventsQuery.class);
        assertThat(result).isNotNull();
        assertThat(result.events())
                .extracting(Event::name)
                .containsExactlyInAnyOrder(
                        "Polska - Argentyna",
                        "Polska - Meksyk",
                        "Polska - Arabia Saudyjska"
                );
    }
}
