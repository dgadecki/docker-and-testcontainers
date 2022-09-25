package pl.dgadecki.dockerandtestcontainers.business.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.dgadecki.dockerandtestcontainers.business.user.domain.service.UserService;
import pl.dgadecki.dockerandtestcontainers.business.user.dto.User;

@Transactional
@RequiredArgsConstructor
public class UserFacadeAdapter implements UserFacade {

    private final UserService userService;

    @Override
    public User getAuthenticatedUser() {
        return userService.getAuthenticatedUser();
    }
}
