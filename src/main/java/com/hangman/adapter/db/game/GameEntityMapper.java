package com.hangman.adapter.db.game;

import com.hangman.domain.game.GameState;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Component
class GameEntityMapper {

    GameState toDomain(GameEntity gameEntity) {
        return GameState
                .builder()
                .id(gameEntity.getId())
                .gameStatus(gameEntity.getGameStatus())
                .word(gameEntity.getWord())
                .usedLetters(gameEntity.getUsedLetters())
                .badCounter(gameEntity.getBadCounter())
                .expectedMoveUserId(gameEntity.getExpectedMoveUserId())
                .userIds(Objects.isNull(gameEntity.getUserIds()) ? Collections.emptySet() : Set.of(gameEntity.getUserIds().split(",")))
                .readyUserIds(Objects.isNull(gameEntity.getReadyUserIds()) ? Collections.emptySet() : Set.of(gameEntity.getReadyUserIds().split(",")))
                .lostUserId(gameEntity.getLostUserId())
                .winnerUserId(gameEntity.getWinnerUserId())
                .build();
    }

    GameEntity toEntity(GameState gameState) {
        return GameEntity
                .builder()
                .id(gameState.getId())
                .gameStatus(gameState.getGameStatus())
                .word(gameState.getWord())
                .usedLetters(gameState.getUsedLetters())
                .badCounter(gameState.getBadCounter())
                .expectedMoveUserId(gameState.getExpectedMoveUserId())
                .userIds(gameState.getUserIds().isEmpty() ? null : String.join(",", gameState.getUserIds()))
                .readyUserIds(gameState.getReadyUserIds().isEmpty() ? null : String.join(",", gameState.getReadyUserIds()))
                .lostUserId(gameState.getLostUserId())
                .winnerUserId(gameState.getWinnerUserId())
                .build();
    }
}
