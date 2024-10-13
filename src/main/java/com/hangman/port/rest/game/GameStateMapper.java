package com.hangman.port.rest.game;

import com.hangman.domain.game.GameState;
import org.springframework.stereotype.Component;

@Component
class GameStateMapper {

    GameStateDto toDto(GameState gameState) {
        final var maskedWord = maskWord(gameState.getWord(), gameState.getUsedLetters());
        return GameStateDto
                .builder()
                .id(gameState.getId())
                .word(gameState.getWord())
                .maskedWord(maskedWord)
                .usedLetters(gameState.getUsedLetters())
                .badCounter(gameState.getBadCounter())
                .expectedMoveUserId(gameState.getExpectedMoveUserId())
                .lostUserId(gameState.getLostUserId())
                .readyUserIds(gameState.getReadyUserIds())
                .userIds(gameState.getUserIds())
                .status(gameState.getGameStatus())
                .winnerUserId(gameState.getWinnerUserId())
                .build();
    }

    private String maskWord(String word, String usedLetters) {
        final var maskedWordBuilder = new StringBuilder();
        for (final var character : word.toCharArray()) {
            if (usedLetters.indexOf(character) != -1) {
                maskedWordBuilder.append(character);
            } else {
                maskedWordBuilder.append("_");
            }
        }
        return maskedWordBuilder.toString();
    }
}
