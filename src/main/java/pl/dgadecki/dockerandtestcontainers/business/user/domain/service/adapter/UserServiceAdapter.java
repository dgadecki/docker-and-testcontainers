package pl.dgadecki.dockerandtestcontainers.business.user.domain.service.adapter;

import pl.dgadecki.dockerandtestcontainers.business.user.domain.service.UserService;
import pl.dgadecki.dockerandtestcontainers.business.user.dto.User;

import java.util.UUID;

public record UserServiceAdapter() implements UserService {

    @Override
    public User getAuthenticatedUser() {
        return User.builder()
                .uuid(UUID.fromString("cfa53334-a564-40ae-9d42-7edfc07e01f9"))
                .firstName("Damian")
                .lastName("Gadecki")
                .build();
    }
}
