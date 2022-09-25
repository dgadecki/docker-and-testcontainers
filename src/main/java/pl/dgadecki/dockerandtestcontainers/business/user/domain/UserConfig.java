package pl.dgadecki.dockerandtestcontainers.business.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.dgadecki.dockerandtestcontainers.business.user.domain.service.UserService;
import pl.dgadecki.dockerandtestcontainers.business.user.domain.service.adapter.UserServiceAdapter;

@Configuration
public class UserConfig {

    @Bean
    public UserFacade userFacade() {
        // Services
        UserService userService = new UserServiceAdapter();

        return new UserFacadeAdapter(
                userService
        );
    }
}
