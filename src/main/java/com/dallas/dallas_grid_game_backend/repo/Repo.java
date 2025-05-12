package com.dallas.dallas_grid_game_backend.repo;

import com.dallas.dallas_grid_game_backend.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface Repo extends JpaRepository<Game, Long> {
    Boolean existsByCreatedDate(Date today); // Rename to follow Spring convention

    Optional<Game> findByCreatedDate(Date today);
}

