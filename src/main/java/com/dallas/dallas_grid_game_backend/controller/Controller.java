package com.dallas.dallas_grid_game_backend.controller;

import com.dallas.dallas_grid_game_backend.entity.Game;
import com.dallas.dallas_grid_game_backend.pojo.GridSpaces;
import com.dallas.dallas_grid_game_backend.repo.Repo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
@Slf4j
@RequestMapping("api/server/game")
public class Controller {
    public final Repo repo;

    public Controller(Repo repo) {
        this.repo = repo;
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> gameExists() {
        // check if a game exists in the database
        Date today = new Date(System.currentTimeMillis());
        boolean exists = repo.existsByCreatedDate(today);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/saved-board")
    //todo there may be an issue here which is causing the gameboard to not render when it is called from the frontend.
    public ResponseEntity<List<List<GridSpaces>>> getGame() {
        Date today = new Date(System.currentTimeMillis());
        Optional<Game> optionalGame = repo.findByCreatedDate(today);
        if (optionalGame.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<GridSpaces> flatGrid = optionalGame.get().getGridList();
        int maxRow = flatGrid.stream().mapToInt(GridSpaces::getRow).max().orElse(0);
        int maxCol = flatGrid.stream().mapToInt(GridSpaces::getCol).max().orElse(0);

        List<List<GridSpaces>> grid = new ArrayList<>();
        for (int i = 0; i <= maxRow; i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j <= maxCol; j++) {
                grid.get(i).add(null);
            }
        }

        for (GridSpaces space : flatGrid) {
            grid.get(space.getRow()).set(space.getCol(), space);
        }

        return ResponseEntity.ok(grid);
    }

    @PostMapping("/create")
    public void createGame(@RequestBody List<List<GridSpaces>> grid) throws JsonProcessingException {
        //insert the game board into the database
        List<GridSpaces> gridList = new ArrayList<>();
        for (int row = 0; row < grid.size(); row++) {
            List<GridSpaces> rowList = grid.get(row);
            for (int col = 0; col < rowList.size(); col++) {
                GridSpaces record = rowList.get(col);
                gridList.add(GridSpaces.builder()
                        .row(row)
                        .col(col)
                        .color(record.getColor())
                        .type(record.getType())
                        .build());
            }
        }
        Game game = new Game();
        game.setCreatedDate(new Date(System.currentTimeMillis()));
        game.setGridList(gridList);
        repo.save(game);
    }

}
