package pl.dgadecki.dockerandtestcontainers.business.event.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dgadecki.dockerandtestcontainers.business.place.domain.model.PlaceSeatEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_event_ticket")
public class EventTicketEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sg_event_ticket")
    @SequenceGenerator(name = "sg_event_ticket", sequenceName = "sq_event_ticket", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "event_id")
    private Long eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", updatable = false, insertable = false)
    private EventEntity eventEntity;

    @NotNull
    @Column(name = "place_seat_id")
    private Long placeSeatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_seat_id", updatable = false, insertable = false)
    private PlaceSeatEntity placeSeatEntity;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "booking_date_time")
    private LocalDateTime bookingDateTime;

    @Column(name = "sale_date_time")
    private LocalDateTime saleDateTime;
}
