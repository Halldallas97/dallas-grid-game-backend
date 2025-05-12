package com.dallas.dallas_grid_game_backend.pojo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GridSpaces implements Serializable {

    private int row;
    private int col;
    private String type;
    private String color;

}
