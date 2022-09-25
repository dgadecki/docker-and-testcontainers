package pl.dgadecki.dockerandtestcontainers.business.place.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_place_seat")
public class PlaceSeatEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sg_place_seat")
    @SequenceGenerator(name = "sg_place_seat", sequenceName = "sq_place_seat", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "place_id")
    private Long placeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", updatable = false, insertable = false)
    private PlaceEntity placeEntity;

    @NotNull
    @Column(name = "sector")
    private String sector;

    @NotNull
    @Column(name = "row")
    private Long row;

    @NotNull
    @Column(name = "number_in_row")
    private Long numberInRow;
}
