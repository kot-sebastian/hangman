package com.hangman.adapter.db.game;

import com.hangman.domain.game.GameState;

import java.util.Collection;
import java.util.Optional;

public interface GameRepository {

    Optional<GameState> findById(Long id);

    GameState saveOrUpdate(GameState gameState);

    void deleteById(Long id);

    Collection<GameState> findByUserId(String userId);
}
