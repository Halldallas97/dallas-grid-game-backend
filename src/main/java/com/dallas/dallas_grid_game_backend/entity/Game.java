package com.dallas.dallas_grid_game_backend.entity;

import com.dallas.dallas_grid_game_backend.configuration.GridSpacesConverter;
import com.dallas.dallas_grid_game_backend.pojo.GridSpaces;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long gameId;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "spaces", nullable = false, columnDefinition = "TEXT")
    @Convert(converter = GridSpacesConverter.class)
    private List<GridSpaces> gridList;

}
