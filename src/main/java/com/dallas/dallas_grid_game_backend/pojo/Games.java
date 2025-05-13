package com.dallas.dallas_grid_game_backend.pojo;

import com.dallas.dallas_grid_game_backend.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@Builder
@AllArgsConstructor
public class Games {
    private Game games;
}
