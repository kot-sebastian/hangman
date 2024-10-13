package com.hangman.domain.game;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.hangman.domain.game.GameStatus.IN_PROGRESS;
import static com.hangman.domain.game.GameStatus.LOBBY;

@Component
class GameValidationService {

    void validateMove(GameState gameState, String userId) {
        gameIsInProgress(gameState);
        itIsUsersTurn(gameState, userId);
    }

    void gameIsInProgress(GameState gameState) {
        if (!Objects.equals(gameState.getGameStatus(), IN_PROGRESS)) {
            throw new RuntimeException("Game is not in progress");
        }
    }

    void gameIsInLobby(GameState gameState) {
        if (!Objects.equals(gameState.getGameStatus(), LOBBY)) {
            throw new RuntimeException("Game is not in lobby");
        }
    }

    void itIsUsersTurn(GameState gameState, String userId) {
        if (!Objects.equals(gameState.getExpectedMoveUserId(), userId)) {
            throw new RuntimeException("It's not your turn");
        }
    }

    void userIsAssignedToGame(GameState gameState, String userId) {
        if (!gameState.getUserIds().contains(userId)) {
            throw new RuntimeException("User not assigned to game");
        }
    }
}
