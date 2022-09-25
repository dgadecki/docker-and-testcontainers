package pl.dgadecki.dockerandtestcontainers.business.user.domain.service;

import pl.dgadecki.dockerandtestcontainers.business.user.dto.User;

/**
 * Defines the business logic of user related processes.
 */
public interface UserService {

    /**
     * Returns currently authenticated user.
     *
     * @return {@link User} with the data of the currently authenticated user
     */
    User getAuthenticatedUser();
}
