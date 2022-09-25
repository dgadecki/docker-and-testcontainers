package pl.dgadecki.dockerandtestcontainers.business.event.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dgadecki.dockerandtestcontainers.business.event.domain.model.EventEntity;
import pl.dgadecki.dockerandtestcontainers.business.event.dto.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query(
            """
            SELECT new pl.dgadecki.dockerandtestcontainers.business.event.dto.Event(
                event.id,
                event.name,
                event.description,
                event.startDateTime
            )
            FROM EventEntity event
            WHERE UPPER(event.name) LIKE UPPER(CONCAT('%', :name, '%'))
            """
    )
    List<Event> findAllEventsByName(@Param("name") String name);
}
